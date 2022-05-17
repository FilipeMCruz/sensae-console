function decodePayload(payload, port) {
  return decoder(base64ToHex(payload), port);
}

const base64ToHex = (() => {
  const values = [],
    output = [];

  return function base64ToHex(txt) {
    if (output.length <= 0) populateLookups();
    const result = [];
    let v1, v2, v3, v4;
    for (let i = 0, len = txt.length; i < len; i += 4) {
      v1 = values[txt.charCodeAt(i)];
      v2 = values[txt.charCodeAt(i + 1)];
      v3 = values[txt.charCodeAt(i + 2)];
      v4 = values[txt.charCodeAt(i + 3)];
      result.push(
        output[(v1 << 2) | (v2 >> 4)],
        output[((v2 & 15) << 4) | (v3 >> 2)],
        output[((v3 & 3) << 6) | v4]
      );
    }
    if (v4 === 64) result.splice(v3 === 64 ? -2 : -1);
    return result;
  };

  function populateLookups() {
    const keys =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    for (let i = 0; i < 256; i++) {
      output.push(("0" + i.toString(16)).slice(-2));
      values.push(0);
    }
    for (let i = 0; i < 65; i++) values[keys.charCodeAt(i)] = i;
  }
})();

function decoder(bytes, port) {
  let latitude =
    ((bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | bytes[3]) /
    1000000;

  let longitude =
    ((bytes[4] << 24) | (bytes[5] << 16) | (bytes[6] << 8) | bytes[7]) /
    1000000;

  const alarm = bytes[8] & 0x40 ? true : false;

  const batV = (((bytes[8] & 0x3f) << 8) | bytes[9]) / 1000;

  let motion_mode = "Disable";
  if (bytes[10] & (0xc0 === 0x40)) {
    motion_mode = "Move";
  } else if (bytes[10] & (0xc0 === 0x80)) {
    motion_mode = "Collide";
  } else if (bytes[10] & (0xc0 === 0xc0)) {
    motion_mode = "User";
  }

  const led_updown = bytes[10] & 0x20 ? "ON" : "OFF";

  const firmware = 160 + (bytes[10] & 0x1f);

  const roll = ((bytes[11] << 8) | bytes[12]) / 100;

  const pitch = ((bytes[13] << 8) | bytes[14]) / 100;

  let hdop = bytes[15];
  if (bytes[15] > 0) {
    hdop = bytes[15] / 100;
  }

  const altitude = ((bytes[16] << 8) | bytes[17]) / 100;

  return {
    0: {
      gps: {
        latitude: latitude,
        longitude: longitude,
        altitude: altitude,
      },
      trigger: {
       value: alarm,
      },
      battery: {
       volts: batV,
      },
      roll: roll, // this values are ignored since the model does not expect them
      pitch: pitch, // this values are ignored since the model does not expect them
      MD: motion_mode, // this values are ignored since the model does not expect them
      LON: led_updown, // this values are ignored since the model does not expect them
      firmware: firmware, // this values are ignored since the model does not expect them
      HDOP: hdop, // this values are ignored since the model does not expect them
      accuracy: 3, // this values are ignored since the model does not expect them
    },
  };
}

function convert(object) {
  return {
    dataId: object.uuid,
    reportedAt: object.reported_at,
    device: {
      id: object.id,
      name: object.name,
      downlink: object.downlink_url,
    },
    measures: decodePayload(object.payload, object.port),
  };
}
