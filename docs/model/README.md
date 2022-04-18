# Model

This document describes the latest version of the data model used in the system.

Current version:

- `iot-core` : `0.1.13`
- `system` : `0.6.0`

## Introduction

This model comes into play once the IoT data is processed, either by a `Data Processor Slave` or by a `Data Decoder Slave` container.

Containers such as `Device Records Slave` or `Fleet Management Backend` expect this specific data structure and semantics.

This model is described by the [iot-core](https://github.com/ShareSpotPT/iot-core) package.

## Data Model

The current data model is represented here as a Json Schema.

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
        "airHumidity": {
            "gramsPerCubicMeter": [float],
            "relativePercentage": [float]
        },
        "airPressure": {
            "hectoPascal": [float]
        },
        "aqi": {
            "value": [float]
        },
        "battery": {
            "percentage": [float],
            "volts": [float],
            "maxVolts": [float],
            "minVolts": [float]
        },
        "co2": {
            "ppm": [float]
        },
        "co": {
            "ppm": [float]
        },
        "distance": {
            "millimeters": [float],
            "maxMillimeters": [float],
            "minMillimeters": [float]
        },
        "gps": {
            "latitude": [double],
            "longitude": [double],
            "altitude": [float]
        },
        "illuminance": {
            "lux": [optional]
        },
        "motion": {
            "value": ["ACTIVE", "INACTIVE" or "UNKNOWN"]
        }, 
        "nh3": {
            "ppm": [float]
        },
        "no2": {
            "ppm": [float]
        },
        "o3": {
            "ppm": [float]
        },
        "occupation" {
            "percentage": [float]
        },
        "ph": {
            "value": [float]
        },
        "pm2_5": {
            "microGramsPerCubicMeter": [float]
        },
        "pm10": {
            "microGramsPerCubicMeter": [float]
        },
        "soilConductivity": {
            "microSiemensPerCentimeter": [float]
        },
        "soilMoisture": {
            "relativePercentage": [float]
        },
        "temperature": {
            "celsius": [float]
        },
        "trigger": {
            "value": [boolean]
        },
        "velocity": {
            "kilometerPerHour": [float]
        },
        "voc": {
            "ppm": [float]
        },
        "waterPressure": {
            "bar": [float]
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
        "airHumidity": {
            "gramsPerCubicMeter": [optional],
            "relativePercentage": [optional]
        },
        "airPressure": {
            "hectoPascal": [optional]
        },
        "aqi": {
            "value": [optional]
        },
        "battery": {
            "percentage": [optional],
            "volts": [optional],
            "maxVolts": [optional],
            "minVolts": [optional]
        },
        "co2": {
            "ppm": [optional]
        },
        "co": {
            "ppm": [optional]
        },
        "distance": {
            "millimeters": [optional],
            "maxMillimeters": [optional],
            "minMillimeters": [optional]
        },
        "gps": {
            "latitude": [optional],
            "longitude": [optional],
            "altitude": [optional]
        },
        "illuminance": {
            "lux": [optional]
        },
        "motion": {
            "value": ["ACTIVE", "INACTIVE" or "UNKNOWN"]
        }, 
        "nh3": {
            "ppm": [optional]
        },
        "no2": {
            "ppm": [optional]
        },
        "o3": {
            "ppm": [optional]
        },
        "occupation" {
            "percentage": [optional]
        },
        "ph": {
            "value": [optional]
        },
        "pm2_5": {
            "microGramsPerCubicMeter": [optional]
        },
        "pm10": {
            "microGramsPerCubicMeter": [optional]
        },
        "soilConductivity": {
            "microSiemensPerCentimeter": [optional]
        },
        "soilMoisture": {
            "relativePercentage": [optional]
        },
        "temperature": {
            "celsius": [optional]
        },
        "trigger": {
            "value": [optional]
        },
        "velocity": {
            "kilometerPerHour": [optional]
        },
        "voc": {
            "ppm": [optional]
        },
        "waterPressure": {
            "bar": [optional]
        }
    }
}
```

The units used to measure the given values are:

- `reported_at`: unix time stamps in milliseconds;
- `data.gps`: geographic coordinate system, with latitude, longitude and optionally altitude;
- `data.gps.latitude`: value between -90 and 90;
- `data.gps.longitude`: value between -180 and 180;
- `data.gps.altitude`: value im meters, 0m equals the sea level;
- `data.motion.value`: `ACTIVE`, `INACTIVE` or `UNKNOWN`;
- `data.velocity.kilometerPerHour`: value in km/h;
- `data.temperature.celsius`: value in celsius;
- `data.aqi.value`: value in AQI [ref](https://www.airnow.gov/aqi/);
- `data.airHumidity.gramsPerCubicMeter`: value with grams of water per cubic meter in the air;
- `data.airHumidity.relativePercentage`: value with relative percentage of water in the air;
- `data.airPressure.hectoPascal`: value in hPa related to the current air pressure;
- `data.battery.volts`: value in volts;
- `data.battery.percentage`: value representing the percentage of battery still available;
- `data.battery.maxVolts`: value representing the minimum volts in the battery needed for the sensor to work;
- `data.battery.minVolts`: value representing the max volts the battery can hold;
- `data.soilMoisture.relativePercentage`: value representing the percentage of water in the soil;
- `data.illuminance.lux`: value representing luminous flux per unit area;
- `data.trigger.value`: true or false / on or off;

Due to lack a of discussion and tests some data is missing a well-defined unit of measurement.

## Further Discussion

As always, changes/improvements to this data model are expected.
