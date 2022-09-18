import http from "k6/http";
import { SharedArray } from "k6/data";
import { check, sleep } from "k6";
import { Trend } from "k6/metrics";
import {
  createDevice,
  randomNumber,
  randomBody,
  initSubscription,
  createSubscription,
  randomId,
  em300THTempLimit,
} from "./helpers.js";
import { subscribeToLiveNotificationsQuery } from "./operations/notification-management.js";
import { anonymousLoginQuery } from "./operations/identity-management.js";
import { insertDevice, clearDevices } from "./database/device-management.js";
import {
  clearNotifications,
  subscribeAnonymous,
  countNotifications,
} from "./database/notification-management.js";
import {
  clearRules,
  createDetectHigherTempRule,
} from "./database/rule-management.js";
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
  initSmartIrrigationDatabase,
} from "./database/smart-irrigation.js";
import {
  publicDomainOid,
  moveDeviceToPublicDomain,
  givePermissionsToPublicDomain,
  clearDomainsDevicesTenants,
  resetIdentity,
  anonymousId,
} from "./database/identity-management.js";
import ws from "k6/ws";
import exec from "k6/execution";

export const options = {
  setupTimeout: "40m",
  teardownTimeout: "10m",
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
      vus: 50,
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

const timeLapseFullTrend = new Trend("time_lapse_full");
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
    data.push(createDevice("em300th", index, true));
  }
  return data;
});

export function subscribe() {
  const res = http.post(`http://${__ENV.SENSAE_INSTANCE_IP}:8086/graphql`, anonymousLoginQuery, {
    headers: { "Content-Type": "application/json" },
  });

  let received = [];
  ws.connect(
    `ws://${__ENV.SENSAE_INSTANCE_IP}:8096/subscriptions`,
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
            new Date().getTime() - message.payload.data.notification.reportedAt
          );
          timeLapseFullTrend.add(
            new Date().getTime() -
              message.payload.data.notification.description.split(";")[1]
          );
          received.push(
            message.payload.data.notification.description.split(";")[0]
          );
          if (Math.abs(received.length - sampleSize[0] * 0.1) < 10)
            closeSocket(socket, received);
        }
      });
      socket.on("open", () => {
        socket.send(initSubscription());
        socket.send(
          createSubscription(subscribeToLiveNotificationsQuery, {
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
  console.log(
    "Expected: " + sampleSize[0] * 0.1 + "; Actual: " + received.length
  );
  received.forEach((dataId) => {
    check(dataId, {
      "notifications were sent": (id) => dataIds.includes(id),
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
    `http://${__ENV.SENSAE_INSTANCE_IP}:8080/sensor-data/${device.channel}/${device.data_type}/${device.device_type}`,
    randomBody(id, device),
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
  check(countNotifications(), {
    "notifications were all stored": (res) =>
      Math.abs(res - sampleSize[0] * 0.1) < 100,
  });
}

export function setup() {
  initSmartIrrigationDatabase();
  data.forEach(insertDevice);
  data.forEach(moveDeviceToPublicDomain);
  givePermissionsToPublicDomain();
  createEM300THProcessor();
  createEM300THDecoder();
  createDetectHigherTempRule(em300THTempLimit(0.9), publicDomainOid());
  subscribeAnonymous(anonymousId());
  var then = new Date();
  then.setMinutes(Math.ceil(then.getMinutes() / 10) * 10);
  var stopFor = (then.getTime() - new Date().getTime() + 2 * 1000 * 60) / 1000;
  console.log(
    "Waiting for " + Math.round(stopFor / 60) + " minutes for rules to apply"
  );
  sleep(stopFor);
}

export function teardown() {
  clearDevices();
  clearProcessors();
  clearDecoders();
  clearDomainsDevicesTenants();
  resetIdentity();
  clearIrrigationData();
  clearNotifications();
  clearRules();
}
