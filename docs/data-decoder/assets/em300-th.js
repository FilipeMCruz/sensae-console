function decodePayload(payload, port) {
  return { "0": decoder(base64ToHex(payload), port) };
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
  var decoded = {};
  var temperature = {};
  var airHumidity = {};
  var battery = {};
  decoded.temperature = temperature;
  decoded.airHumidity = airHumidity;
  decoded.battery = battery; 

  for (var i = 0; i < bytes.length; ) {
    var channel_id = bytes[i++];
    var channel_type = bytes[i++];

    // BATTERY
    if (channel_id === 0x01 && channel_type === 0x75) {
      battery.percentage = bytes[i];
      i += 1;
    }
    // TEMPERATURE
    else if (channel_id === 0x03 && channel_type === 0x67) {
      // ℃
      temperature.celsius = readInt16LE(bytes.slice(i, i + 2)) / 10;
      i += 2;

      // ℉
      // decoded.temperature = readInt16LE(bytes.slice(i, i + 2)) / 10 * 1.8 + 32;
      // i +=2;
    }
    // HUMIDITY
    else if (channel_id === 0x04 && channel_type === 0x68) {
      airHumidity.relativePercentage = bytes[i] / 2;
      i += 1;
    } else {
      break;
    }
  }

  return decoded;
}

/* ******************************************
 * bytes to number
 ********************************************/
function readUInt16LE(bytes) {
  var value = (bytes[1] << 8) + bytes[0];
  return value & 0xffff;
}

function readInt16LE(bytes) {
  var ref = readUInt16LE(bytes);
  return ref > 0x7fff ? ref - 0x10000 : ref;
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
