import http from "k6/http";
import { SharedArray } from "k6/data";
import { check, sleep } from "k6";
import { Trend } from "k6/metrics"
import {
  createLiveDataFilters,
  randomEM300THbody,
  createEM300THDevice,
  randomNumber,
  initSubscription,
  createSubscription,
  randomId,
} from "./helpers.js";
import { subscribeToLiveDataQuery } from "./operations/smart-irrigation.js";
import { anonymousLoginQuery } from "./operations/identity-management.js";
import { insertDevice, clearDevices } from "./database/device-management.js";
import {
  createEM300THProcessor,
  clearProcessors,
} from "./database/data-processor.js";
import {
  createEM300THDecoder,
  clearDecoders,
} from "./database/data-decoder.js";
import {
  clearIrrigationData,
  countSmartIrrigationMeasuresEntries,
  initSmartIrrigationDatabase,
} from "./database/smart-irrigation.js";
import {
  moveDeviceToPublicDomain,
  givePermissionsToPublicDomain,
  clearDomainsDevicesTenants,
  resetIdentity,
} from "./database/identity-management.js";
import ws from "k6/ws";
import exec from "k6/execution";

export const options = {
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
      vus: 100,
      iterations: 100,
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

const timeLapseTrend = new Trend('time_lapse');

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
    data.push(createEM300THDevice(index));
  }
  return data;
});

export function subscribe() {
  const res = http.post("http://localhost:8086/graphql", anonymousLoginQuery, {
    headers: { "Content-Type": "application/json" },
  });

  let received = [];
  ws.connect(
    "ws://localhost:8801/subscriptions",
    {
      headers: {
        "Sec-WebSocket-Protocol": "graphql-transport-ws",
      },
    },
    (socket) => {
      socket.on("message", (msg) => {
        const message = JSON.parse(msg);
        if (message.type == "next") {
          timeLapseTrend.add(new Date().getTime() - message.payload.data.data.reportedAt);
          received.push(message.payload.data.data.dataId);
          if (received.length % 1000 === 0)
            console.log("Received: " + received.length);
          if (received.length === sampleSize[0]) closeSocket(socket, received);
        }
      });
      socket.on("open", () => {
        socket.send(initSubscription());
        socket.send(
          createSubscription(subscribeToLiveDataQuery, {
            filters: createLiveDataFilters(data),
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
    "data units were received": (rec) => rec.length === sampleSize[0],
  });
  received.forEach((dataId) => {
    check(dataId, {
      "data unit was sent": (id) => dataIds.includes(id),
    });
  });
  socket.close();
}

export function ingestion() {
  sleep(randomNumber(0, 1));
  const vu = exec.vu.idInTest - 1; //vus start at 1, arrays at 0;
  const device = data[vu];
  const id = dataIds[vu + (data.length - 2) * exec.vu.iterationInScenario];

  const res = http.post(
    `http://localhost:8080/sensor-data/${device.channel}/${device.data_type}/${device.device_type}`,
    randomEM300THbody(id, device),
    {
      headers: {
        Authorization: `${__ENV.SENSAE_DATA_AUTH_KEY}`,
        "Content-Type": "application/json",
      },
    }
  );
  check(res, { "status was 202": (r) => r.status === 202 });
  sleep(device.interval);
}

export function consumption() {
  var numberEntries = countSmartIrrigationMeasuresEntries();
  check(numberEntries, {
    "data unit were all stored": (res) => res === sampleSize[0],
  });
}

export function setup() {
  initSmartIrrigationDatabase();
  data.forEach(insertDevice);
  data.forEach(moveDeviceToPublicDomain);
  givePermissionsToPublicDomain();
  createEM300THProcessor();
  createEM300THDecoder();
}

export function teardown() {
  sleep(1);
  clearDevices();
  clearProcessors();
  clearDecoders();
  clearDomainsDevicesTenants();
  resetIdentity();
  clearIrrigationData();
}
