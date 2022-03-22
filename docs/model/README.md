# Model

This document describes the latest version of the data model used in the system.

## Introduction

This model comes into play once the IoT data is processed, either by a `Data Processor Slave` or by a `Data Decoder Slave` container.

Containers such as `Device Records Slave` or `Fleet Management Backend` expect this specific data structure and semantics.

This model is described by the [iot-core](https://github.com/ShareSpotPT/iot-core) package.

## Data Model

Current version: `0.1.11`

``` json
{
    "dataId": [uuid],
    "reportedAt": [long],
    "device": {
        "id": [uuid],
        "name": [string],
        "records": [
            {
            "entry": {
                "label": [string],
                "content": [string]
            }
        }
        ],
        "domains": {
            "read": [
                [uuid]
            ],
            "readWrite": [
                [uuid]
            ]
        }
    },
    "data": {
        "gps": {
            "latitude": [double],
            "longitude": [double]
        },
        "temperature": {
            "celsius": [double]
        },
        "motion": {
            "value": [enum]
        },
        "velocity": {
            "value": [double]
        },
        "aqi": {
            "value": [double]
        },
        "humidity": {
            "value": [double]
        },
        "pressure": {
            "value": [double]
        }
    }
}
```

At the time of data processing, though `Data Processor Slave` or `Data Decoder Slave` containers, some data is required, optional or rejected (if written).

``` json
{
    "dataId": [required],
    "reportedAt": [required],
    "device": {
        "id": [required],
        "name": [optional],
        "records": [
            {
            "entry": {
                "label": [optional],
                "content": [optional]
            }
        }
        ],
        "domains": {
            "read": [
                [rejected]
            ],
            "readWrite": [
                [rejected]
            ]
        }
    },
    "data": {
        "gps": {
            "latitude": [optional],
            "longitude": [optional]
        },
        "temperature": {
            "celsius": [optional]
        },
        "motion": {
            "value": [optional]
        },
        "velocity": {
            "value": [optional]
        },
        "aqi": {
            "value": [optional]
        },
        "humidity": {
            "value": [optional]
        },
        "pressure": {
            "value": [optional]
        }
    }
}
```

The units used to measure the given values are:

- `reported_at`: unix time stamps in milliseconds,
- `data.gps`: geographic coordinate system, with latitude and longitude,
- `data.gps.latitude`: value between -90 and 90
- `data.gps.longitude`: value between -180 and 180
- `data.motion.value`: `ACTIVE`, `INACTIVE` or `UNKNOWN`
- `data.velocity.value`: value in km/h
- `data.temperature.celsius`: value in celsius
- `data.aqi.value`: value in EAQI
- `data.pressure.value`: value in hPa
- `data.humidity.value`: percentage value

Due to lack a of discussion and tests some data is missing a well-defined unit of measurement.

## Improvement

- add optional property `altitude` to `data.gps`;
- change `value` from `data.velocity` to something that can be understood, like `kmperh`
- change `value` from `data.aqi` to something that can be understood, since there are various scales of `aqi` we must choose one and stick with it, it is suggested that we us, `eaqi` [ref](https://airindex.eea.europa.eu/Map/AQI/Viewer/)
- change `value` from `data.humidity` to something that can be understood, like `percentage` or `gramspercubicmeter`
- change `value` from `data.pressure` to something that can be understood, like `hPa`
- add `data.moisture.percentage` to represent soil moisture

## Further Discussion

As always, changes/improvements to this data model are expected.
