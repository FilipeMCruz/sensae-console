# Data Decoders

This document describes how to create `data decoder`s according to the latest version of the system.

Current version:

- `iot-core` : `0.1.18`
- `system` : `0.10.0`

## Introduction

The `data decoder`s purpose is to provide a flexible way of transforming the data into something that the system [understands](../model/README.md).

This action is performed by a `Data Decoder Slave` container when IoT Data is sent to the HTTP endpoint `/sensor-data/encoded/<sensor-type>`.

To register a new decoder one needs to go to the `Data Decoder` page in the system's UI, define the `<sensor-type>`, write the script and submit the new decoder.

## Rules

There are certain rules when creating the script:

- It has to be written in vanilla javascript;
- It has to have a `main` function with the following signature `function convert(data)`;
- It can't import any node function, npm package or reference other scripts.

## Helper Functions

Since no npm or node packages are available it can be difficult to start writing decoders. To tackle this, and since IoT payload is usually encoded as a base64 string and then read as a hex byte array, here is a function that performs this:

``` js
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
        parseInt(output[(v1 << 2) | (v2 >> 4)],16),
        parseInt(output[((v2 & 15) << 4) | (v3 >> 2)],16),
        parseInt(output[((v3 & 3) << 6) | v4],16)
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
```

## Decoder Examples

Some examples can be found in the `assets` folder.

- `lgt92`: [decoder](assets/lgt92.js).
- `em300-th`: [decoder](assets/em300-th.js).
- `em500-co2`: [decoder](assets/em500-co2.js).

## Further Discussion

As always, changes/improvements to this page and `data decoder`s are expected.
