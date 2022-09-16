import { uuidv4 } from "https://jslib.k6.io/k6-utils/1.4.0/index.js";

export function randomId() {
  return uuidv4();
}

export function randomNumber(min, max) {
  return Math.random() * (max - min) + min;
}

export function randomBoolean() {
  return Math.random() < 0.5;
}

export function randomEM300THbody(dataId, device) {
  return JSON.stringify({
    downlink_url: `https://console-vip.helium.com/api/v1/down/63f40b83-0d90-4ded-aae1-9feff17bc93f/IlEe7-Wdn_dOgWn3LimmtpEjefLBBNl3/${device.id}`,
    temperature: randomNumber(1, 50),
    humidity: randomNumber(1, 90),
    id: device.id,
    name: device.name,
    reported_at: new Date().getTime(),
    uuid: dataId,
  });
}

export function createLiveDataFilters(data) {
  return {
    devices: data.map((d) => d.id),
    irrigationZones: [],
    content: "",
  };
}

export function createEM300THDevice(id) {
  return {
    name: "P" + id.toString().padStart(3, "0"),
    id: uuidv4(),
    lat: randomNumber(36, 44),
    long: -randomNumber(6, 9),
    data_type: "encoded", //randomBoolean() ? "decoded" : "encoded",
    device_type: "em300th",
    channel: "irrigation",
    interval: 2, // randomBoolean() ? 1 : 0.5,
  };
}

export function createSubscription(subscription, variables) {
  return JSON.stringify({
    id: uuidv4(),
    type: "subscribe",
    payload: {
      query: subscription,
      variables: variables,
    },
  });
}

export function initSubscription() {
  return JSON.stringify({ type: "connection_init" });
}
