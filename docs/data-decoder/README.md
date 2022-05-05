# Data Decoders

This document describes how to create `data decoder`s according to the latest version of the system.

Current version:

- `iot-core` : `0.1.15`
- `system` : `0.7.0`

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

Since no npm or node packages are available it can be difficult to start writing decoders, to tackle this here are some common functions:

This function, `decodeBase64`, receives a `Base64` encoded string and decodes it. To save bandwidth the sensor payload usually comes as a `Base64` string.

``` js
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
```

This function, `strToUtf16Bytes`, receives a string and returns a Utf16 byte array. Usually sensor payload documentation assumes that you have a byte array.

``` js
function strToUtf16Bytes(str) {
  const bytes = [];
  for (ii = 0; ii < str.length; ii++) {
    const code = str.charCodeAt(ii); // x00-xFFFF
    bytes.push(code & 255, code >> 8); // low, high
  }
  return bytes;
}
```

This function, `toBytes`, receives a `Base64` encoded string, decodes it and transforms it into a sanitized byte array by using the mentioned functions.

``` js
function toBytes(payload) {
  const array = [];
  const buffer = strToUtf16Bytes(decodeBase64(payload));
  for (let i = 0; i < buffer.length; i++) {
    if (buffer[i] !== 0) array.push(buffer[i]);
  }
  return array;
}
```

As a full example the current `lgt92` sensor decoder can be seen [here](assets/lgt92.js).

## Further Discussion

As always, changes/improvements to this page and `data decoder`s are expected.
