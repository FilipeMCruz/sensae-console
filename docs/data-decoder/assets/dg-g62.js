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

function decodePayload(payload, port) {
  return { 0: decoder(base64ToHex(payload), port) };
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
        parseInt(output[(v1 << 2) | (v2 >> 4)], 16),
        parseInt(output[((v2 & 15) << 4) | (v3 >> 2)], 16),
        parseInt(output[((v3 & 3) << 6) | v4], 16)
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
  var gps = {};
  var battery = {};
  var velocity = {};
  var unused = {};
  var temperature = {};
  var trigger = {};
  var motion = {};

  if (bytes === null) return null;

  if (port === 1) {
    if (bytes.length != 17 && bytes.length < 19) return null;

    unused._type = "full data";

    switch (bytes[0] & 0x3) {
      case 0:
        unused.tripType = "None";
        motion.value = "INACTIVE";
        break;
      case 1:
        unused.tripType = "Ignition";
        motion.value = "ACTIVE";
        break;
      case 2:
        unused.tripType = "Movement";
        motion.value = "ACTIVE";
        break;
      case 3:
        unused.tripType = "Run Detect";
        motion.value = "ACTIVE";
        break;
    }

    gps.latitude =
      (bytes[0] & 0xf0) +
      bytes[1] * 256 +
      bytes[2] * 65536 +
      bytes[3] * 16777216;
    if (gps.latitude >= 0x80000000)
      // 2^31
      gps.latitude -= 0x100000000; // 2^32
    gps.latitude /= 1e7;

    gps.longitude =
      (bytes[4] & 0xf0) +
      bytes[5] * 256 +
      bytes[6] * 65536 +
      bytes[7] * 16777216;
    if (gps.longitude >= 0x80000000)
      // 2^31
      gps.longitude -= 0x100000000; // 2^32
    gps.longitude /= 1e7;

    unused.vExtGood = (bytes[0] & 0x4) !== 0 ? true : false;
    unused.gpsCurrent = (bytes[0] & 0x8) !== 0 ? true : false;

    trigger.value = (bytes[4] & 0x1) !== 0 ? true : false;
    unused.digIn1 = (bytes[4] & 0x2) !== 0 ? true : false;
    unused.digIn2 = (bytes[4] & 0x4) !== 0 ? true : false;
    unused.digOut = (bytes[4] & 0x8) !== 0 ? true : false;

    unused.headingDeg = bytes[8] * 2;
    velocity.kilometerPerHour = bytes[9];
    battery.volts = bytes[10] * 0.02;

    unused.vExt = 0.001 * (bytes[11] + bytes[12] * 256);
    unused.vAin = 0.001 * (bytes[13] + bytes[14] * 256);

    temperature.celsius = bytes[15];
    if (temperature.celsius >= 0x80)
      // 2^7
      temperature.celsius -= 0x100; // 2^8

    unused.gpsAccM = bytes[16];

    // Clean up the floats for display
    gps.latitude = parseFloat(gps.latitude.toFixed(7));
    gps.longitude = parseFloat(gps.longitude.toFixed(7));

    battery.volts = parseFloat(battery.volts.toFixed(3));
    unused.vExt = parseFloat(unused.vExt.toFixed(3));
    unused.vAin = parseFloat(unused.vAin.toFixed(3));
  } else if (port === 2) {
    if (bytes.length != 11) return null;

    unused._type = "data part 1";

    switch (bytes[0] & 0x3) {
      case 0:
        unused.tripType = "None";
        motion.value = "INACTIVE";
        break;
      case 1:
        unused.tripType = "Ignition";
        motion.value = "ACTIVE";
        break;
      case 2:
        unused.tripType = "Movement";
        motion.value = "ACTIVE";
        break;
      case 3:
        unused.tripType = "Run Detect";
        motion.value = "ACTIVE";
        break;
    }

    gps.latitude =
      (bytes[0] & 0xf0) +
      bytes[1] * 256 +
      bytes[2] * 65536 +
      bytes[3] * 16777216;
    if (gps.latitude >= 0x80000000)
      // 2^31
      gps.latitude -= 0x100000000; // 2^32
    gps.latitude /= 1e7;

    gps.longitude =
      (bytes[4] & 0xf0) +
      bytes[5] * 256 +
      bytes[6] * 65536 +
      bytes[7] * 16777216;
    if (gps.longitude >= 0x80000000)
      // 2^31
      gps.longitude -= 0x100000000; // 2^32
    gps.longitude /= 1e7;

    unused.vExtGood = (bytes[0] & 0x4) !== 0 ? true : false;
    unused.gpsCurrent = (bytes[0] & 0x8) !== 0 ? true : false;

    trigger.value = (bytes[4] & 0x1) !== 0 ? true : false;
    unused.digIn1 = (bytes[4] & 0x2) !== 0 ? true : false;
    unused.digIn2 = (bytes[4] & 0x4) !== 0 ? true : false;
    unused.digOut = (bytes[4] & 0x8) !== 0 ? true : false;

    unused.headingDeg = bytes[8] * 2;
    velocity.kilometerPerHour = bytes[9];
    battery.volts = bytes[10] * 0.02;

    // Clean up the floats for display
    gps.latitude = parseFloat(gps.latitude.toFixed(7));
    gps.longitude = parseFloat(gps.longitude.toFixed(7));
    battery.volts = parseFloat(battery.volts.toFixed(3));
  } else if (port === 3) {
    if (bytes.length != 6 && bytes.length < 8) return null;

    unused._type = "data part 2";

    unused.vExt = 0.001 * (bytes[0] + bytes[1] * 256);
    unused.vAin = 0.001 * (bytes[2] + bytes[3] * 256);

    temperature.celsius = bytes[4];
    if (temperature.celsius >= 0x80)
      // 2^7
      temperature.celsius -= 0x100; // 2^8

    unused.gpsAccM = bytes[5];

    // Clean up the floats for display
    unused.vExt = parseFloat(decoded.vExt.toFixed(3));
    unused.vAin = parseFloat(decoded.vAin.toFixed(3));
  } else if (port === 4) {
    if (bytes.length != 8) return null;

    unused._type = "odometer";

    var runtimeS =
      bytes[0] + bytes[1] * 256 + bytes[2] * 65536 + bytes[3] * 16777216;
    unused.runtime =
      Math.floor(runtimeS / 86400) +
      "d" +
      Math.floor((runtimeS % 86400) / 3600) +
      "h" +
      Math.floor((runtimeS % 3600) / 60) +
      "m" +
      (runtimeS % 60) +
      "s";
    unused.distanceKm =
      0.01 *
      (bytes[4] + bytes[5] * 256 + bytes[6] * 65536 + bytes[7] * 16777216);

    // Clean up the floats for display
    unused.distanceKm = parseFloat(unused.distanceKm.toFixed(2));
  } else if (port === 5) {
    if (bytes.length != 3) return null;

    unused._type = "downlink ack";

    unused.sequence = bytes[0] & 0x7f;
    unused.accepted = (bytes[0] & 0x80) !== 0 ? true : false;
    unused.fwMaj = bytes[1];
    unused.fwMin = bytes[2];
  }

  return {
    battery,
    gps,
    velocity,
    temperature,
    motion,
    trigger, // ignition is turned on or off
    unused, // this values are ignored since the model does not expect them
  };
}
