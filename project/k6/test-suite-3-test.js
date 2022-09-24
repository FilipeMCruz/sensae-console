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

export const options = {
  setupTimeout: "10m",
  scenarios: {
    subscribe: {
      executor: "shared-iterations",
      startTime: "0s",
      vus: 1,
      iterations: 1,
      maxDuration: "5m",
      exec: "subscribe",
    },
    ingestion: {
      executor: "per-vu-iterations",
      vus: 10,
      iterations: 10,
      startTime: "5s",
      exec: "ingestion",
      maxDuration: "5m",
    },
    consumption: {
      executor: "shared-iterations",
      startTime: "5m",
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
    options.scenarios.ingestion.vus * options.scenarios.ingestion.iterations
  );
  return sampleSize;
});

const dataIds = new SharedArray("dataIds", function () {
  const dataIds = [];
  // +2 since we don't know what vus will be assigned to ingestion and it can't fail
  const numberOfDataUnits =
    options.scenarios.ingestion.vus *
    (options.scenarios.ingestion.iterations + 2);
  for (let index = 0; index < numberOfDataUnits; index++) {
    dataIds.push(randomId());
  }
  return dataIds;
});

const data = new SharedArray("data", function () {
  const data = [];
  // +2 since we don't know what vus will be assigned to ingestion and it can't fail
  const total = options.scenarios.ingestion.vus + 2;
  for (let index = 0; index < total; index++) {
    let device = createDevice("fleet", "lgt92", index, false);
    device.route = createRoute(options.scenarios.ingestion.iterations);
    data.push(device);
  }
  return data;
});

export function subscribe() {
  const res = http.post(
    `http://${__ENV.SENSAE_INSTANCE_IP}:8086/graphql`,
    anonymousLoginQuery,
    {
      headers: { "Content-Type": "application/json" },
    }
  );

  let received = [];
  ws.connect(
    `ws://${__ENV.SENSAE_INSTANCE_IP}:8801/subscriptions`,
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
            new Date().getTime() - message.payload.data.data.reportedAt
          );
          received.push(message.payload.data.data.dataId);
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
      socket.setTimeout(() => closeSocket(socket, received), 300000);
    }
  );
}

export function closeSocket(socket, received) {
  console.log("Expected: " + sampleSize[0] + "; Actual: " + received.length);
  check(received, {
    "all data units were received": (rec) => rec.length === sampleSize[0],
  });
  received.forEach((dataId) => {
    check(dataId, {
      "correct data units were received": (id) => dataIds.includes(id),
    });
  });
  socket.close();
}

export function ingestion() {
  const vu = exec.vu.idInTest - 1; //vus start at 1, arrays at 0;
  const device = data[vu];
  const id = dataIds[vu + (data.length - 2) * exec.vu.iterationInScenario];

  if (exec.vu.iterationInScenario == 0) {
    sleep(randomNumber(0, 2));
  }

  sleep(device.interval);
  const res = http.post(
    `https://${__ENV.SENSAE_INSTANCE_IP}:8443/sensor-data/${device.channel}/${device.data_type}/${device.device_type}`,
    randomBody(id, device, exec.vu.iterationInScenario),
    {
      headers: {
        Authorization: `${__ENV.SENSAE_DATA_AUTH_KEY}`,
        "Content-Type": "application/json",
      },
    }
  );
  check(res, { "status was 202": (r) => r.status === 202 });
}

export function consumption() {
  var numberEntries = countFleetMeasuresEntries();
  console.log("Data Units stored: " + numberEntries);
  check(numberEntries, {
    "data units were all stored": (res) => res === sampleSize[0],
  });
}

export function setup() {
  data.forEach(insertDevice);
  data.forEach(moveDeviceToPublicDomain);
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
