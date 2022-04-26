function decodePayload(payload, port) {
  return decoder(toBytes(payload), port);
}

function decodeBase64(s) {
  var e = {},
    i,
    b = 0,
    c,
    x,
    l = 0,
    a,
    r = "",
    w = String.fromCharCode,
    L = s.length;
  var A = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  for (i = 0; i < 64; i++) {
    e[A.charAt(i)] = i;
  }
  for (x = 0; x < L; x++) {
    c = e[s.charAt(x)];
    b = (b << 6) + c;
    l += 6;
    while (l >= 8) {
      ((a = (b >>> (l -= 8)) & 0xff) || x < L - 2) && (r += w(a));
    }
  }
  return r;
}

function strToUtf16Bytes(str) {
  const bytes = [];
  for (ii = 0; ii < str.length; ii++) {
    const code = str.charCodeAt(ii); // x00-xFFFF
    bytes.push(code & 255, code >> 8); // low, high
  }
  return bytes;
}

function toBytes(payload) {
  const array = [];
  const buffer = strToUtf16Bytes(decodeBase64(payload));
  for (let i = 0; i < buffer.length; i++) {
    if (buffer[i] !== 0) array.push(buffer[i]);
  }
  return array;
}

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

  const Firmware = 160 + (bytes[10] & 0x1f);

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
      //trigger: {
      //  value: alarm,
      //},
      //battery: {
      //  volts: batV,
      //},
      roll: roll, // this values are ignored since the model does not expect them
      pitch: pitch, // this values are ignored since the model does not expect them
      MD: motion_mode, // this values are ignored since the model does not expect them
      LON: led_updown, // this values are ignored since the model does not expect them
      FW: Firmware, // this values are ignored since the model does not expect them
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
    },
    measures: decodePayload(object.payload, object.port),
  };
}
