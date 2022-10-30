import http from "k6/http";
import { SharedArray } from "k6/data";
import { check, sleep } from "k6";
import { Trend } from "k6/metrics";
import {
  createDevice,
  createRoute,
  randomNumber,
  randomBody,
  initSubscription,
  createSubscription,
  randomId,
} from "./helpers.js";
import { anonymousLoginQuery } from "./operations/identity-management.js";
import { subscribeToLiveGPSDataQuery } from "./operations/fleet-management.js";
import { insertDevice, clearDevices } from "./database/device-management.js";
import {
  createLGT92Processor,
  clearProcessors,
} from "./database/data-processor.js";
import { createLGT92Decoder, clearDecoders } from "./database/data-decoder.js";
import {
  clearFleetData,
  countFleetMeasuresEntries,
} from "./database/fleet-management.js";
import {
  moveDeviceToPublicDomain,
  givePermissionsToPublicDomain,
  clearDomainsDevicesTenants,
  resetIdentity,
} from "./database/identity-management.js";
import ws from "k6/ws";
import exec from "k6/execution";

const n_devices = 1000; // 50, 100, 200, 500, 1000
const interval = 10;
const group_by = 5;

export const options = {
  setupTimeout: "10m",
  scenarios: {
    subscribe: {
      executor: "shared-iterations",
      startTime: "0s",
      vus: 1,
      iterations: 1,
      maxDuration: "4m",
      exec: "subscribe",
    },
    ingestion: {
      executor: "per-vu-iterations",
      vus: n_devices / group_by,
      iterations: 10,
      startTime: "5s",
      exec: "ingestion",
      maxDuration: "4m",
    },
    consumption: {
      executor: "shared-iterations",
      startTime: "4m",
      vus: 1,
      iterations: 1,
      maxDuration: "10s",
      exec: "consumption",
    },
  },
};

const timeLapseTrend = new Trend("time_lapse");

const sampleSize = new SharedArray("sampleSize", function () {
  const sampleSize = [];
  sampleSize.push(
    options.scenarios.ingestion.vus *
      options.scenarios.ingestion.iterations *
      group_by
  );
  return sampleSize;
});

const dataIds = new SharedArray("dataIds", function () {
  const dataIds = [];
  // +2 since we don't know what vus will be assigned to ingestion and it can't fail

  for (let inter = 0; inter < options.scenarios.ingestion.iterations; inter++) {
    let interArr = [];
    for (let index = 0; index < options.scenarios.ingestion.vus + 2; index++) {
      let arr = [];
      for (var i = 0; i < group_by; i++) {
        arr.push(randomId());
      }
      interArr.push(arr);
    }
    dataIds.push(interArr);
  }

  return dataIds;
});

const data = new SharedArray("data", function () {
  const data = [];
  // +2 since we don't know what vus will be assigned to ingestion and it can't fail
  const total = options.scenarios.ingestion.vus + 2;
  for (let index = 0; index < total; index++) {
    let arr = [];
    for (var i = 0; i < group_by; i++) {
      let device = createDevice("fleet", "lgt92", index, false);
      device.route = createRoute(options.scenarios.ingestion.iterations);
      arr.push(device);
    }
    data.push(arr);
  }
  return data;
});

export function subscribe() {
  const res = http.post(
    `http://${__ENV.SENSAE_INSTANCE_IP}:8086/graphql`,
    anonymousLoginQuery,
    { headers: { "Content-Type": "application/json" } }
  );

  let received = [];
  ws.connect(
    `ws://${__ENV.SENSAE_INSTANCE_IP}:8800/subscriptions`,
    {
      headers: {
        "Sec-WebSocket-Protocol": "graphql-transport-ws",
      },
    },
    (socket) => {
      socket.on("message", (msg) => {
        const message = JSON.parse(msg);
        if (message.type == "next") {
          timeLapseTrend.add(
            new Date().getTime() - message.payload.data.locations.reportedAt,
            { iteration: pickIteration(message.payload.data.locations.dataId) }
          );
          received.push(message.payload.data.locations.dataId);
          if (received.length % 1000 === 0)
            console.log("Received " + received.length + " messages");
          if (received.length === sampleSize[0]) closeSocket(socket, received);
        }
      });
      socket.on("open", () => {
        socket.send(initSubscription());
        socket.send(
          createSubscription(subscribeToLiveGPSDataQuery, {
            Authorization:
              "Bearer " + JSON.parse(res.body).data.anonymous.token,
          })
        );
      });
      socket.setTimeout(function timeout() {
        closeSocket(socket, received);
      }, 1800000);
    }
  );
}

function pickIteration(dataId) {
  return dataIds.findIndex((ids) => ids.some((idss) => idss.includes(dataId)));
}

export function closeSocket(socket, received) {
  const allDataUnits = dataIds.flat(2);
  console.log("Expected: " + sampleSize[0] + "; Actual: " + received.length);
  check(received, {
    "all data units were received": (rec) => rec.length === sampleSize[0],
  });
  received.forEach((dataId) => {
    check(dataId, {
      "correct data units were received": (id) => allDataUnits.includes(id),
    });
  });
  socket.close();
}

export function ingestion() {
  const vu = exec.vu.idInTest - 1; //vus start at 1, arrays at 0;
  const devices = data[vu];
  const ids = dataIds[exec.vu.iterationInScenario][vu];

  if (exec.vu.iterationInScenario === 0) sleep(randomNumber(0, interval));

  for (let index = 0; index < devices.length; index++) {
    const device = devices[index];

    const res = http.post(
      `https://${__ENV.SENSAE_INSTANCE_IP}:8443/sensor-data/${device.channel}/${device.data_type}/${device.device_type}`,
      randomBody(ids[index], device, exec.vu.iterationInScenario),
      {
        headers: {
          Authorization: `${__ENV.SENSAE_DATA_AUTH_KEY}`,
          "Content-Type": "application/json",
        },
      }
    );
    check(res, { "status was 202": (r) => r.status === 202 });
  }
  if (exec.vu.iterationInScenario !== 9) sleep(interval);
}

export function consumption() {
  var numberEntries = countFleetMeasuresEntries();
  console.log("Data Units stored: " + numberEntries);
  check(numberEntries, {
    "data units were all stored": (res) => res === sampleSize[0] * 2,
  });
}

export function setup() {
  data.flat().forEach(insertDevice);
  data.flat().forEach(moveDeviceToPublicDomain);
  givePermissionsToPublicDomain();
  createLGT92Processor();
  createLGT92Decoder();
}

export function teardown() {
  clearDevices();
  clearProcessors();
  clearDecoders();
  clearDomainsDevicesTenants();
  resetIdentity();
  clearFleetData();
}
