import http from "k6/http";
import { SharedArray } from "k6/data";
import { check, sleep } from "k6";
import {
  createDevice,
  createRoute,
  randomNumber,
  randomBody,
  randomId,
} from "./helpers.js";
import { insertDevice, clearDevices } from "./database/device-management.js";
import {
  createLGT92Processor,
  clearProcessors,
} from "./database/data-processor.js";
import { createLGT92Decoder, clearDecoders } from "./database/data-decoder.js";
import {
  clearFleetData,
} from "./database/fleet-management.js";
import {
  moveDeviceToPublicDomain,
  givePermissionsToPublicDomain,
  clearDomainsDevicesTenants,
  resetIdentity,
} from "./database/identity-management.js";
import exec from "k6/execution";

const n_devices = 200; // 50, 100, 200, 500, 1000
const interval = 3;
const group_by = 5;

export const options = {
  setupTimeout: "10m",
  scenarios: {
    ingestion: {
      executor: "per-vu-iterations",
      vus: n_devices / group_by,
      iterations: 10,
      exec: "ingestion",
      maxDuration: "4m",
    },
  },
};

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

export function ingestion() {
  const vu = exec.vu.idInTest - 1; //vus start at 1, arrays at 0;
  const devices = data[vu];
  const ids = dataIds[exec.vu.iterationInScenario][vu];

  if (exec.vu.iterationInScenario === 0) sleep(randomNumber(0, interval));

  for (let index = 0; index < devices.length; index++) {
    const device = devices[index];

    const res = http.post(
      `https://${__ENV.SENSAE_INSTANCE_IP}:8443/sensor-data/c${exec.vu.iterationInScenario}/${device.data_type}/${device.device_type}`,
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
