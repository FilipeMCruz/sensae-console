# Model

This document describes the latest version of the data model used in the system.

Current version:

- `iot-core` : `0.1.13`
- `system` : `0.6.0`

## Introduction

This model comes into play once the IoT data is processed, either by a `measures.{number} Processor Slave` or by a `measures.{number} Decoder Slave` container.

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
    "measures": {
        [int]: {
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
            "occupation": {
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
}
```

At the time of data processing, though `measures.{number} Processor Slave` or `measures.{number} Decoder Slave` containers, some data is required, optional or rejected (if written).

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
    "measures": {
        [int]: {
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
            "occupation": {
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
}
```

The units used to measure the given values are:

- `reported_at`: unix time stamps in milliseconds;
- `measures.{number}.gps`: geographic coordinate system, with latitude, longitude and optionally altitude;
- `measures.{number}.gps.latitude`: value between -90 and 90;
- `measures.{number}.gps.longitude`: value between -180 and 180;
- `measures.{number}.gps.altitude`: value im meters, 0m equals the sea level;
- `measures.{number}.motion.value`: `ACTIVE`, `INACTIVE` or `UNKNOWN`;
- `measures.{number}.velocity.kilometerPerHour`: value in km/h;
- `measures.{number}.temperature.celsius`: value in celsius;
- `measures.{number}.aqi.value`: value in AQI [ref](https://www.airnow.gov/aqi/);
- `measures.{number}.airHumidity.gramsPerCubicMeter`: value with grams of water per cubic meter in the air;
- `measures.{number}.airHumidity.relativePercentage`: value with relative percentage of water in the air;
- `measures.{number}.airPressure.hectoPascal`: value in hPa related to the current air pressure;
- `measures.{number}.battery.volts`: value in volts;
- `measures.{number}.battery.percentage`: value representing the percentage of battery still available;
- `measures.{number}.battery.maxVolts`: value representing the minimum volts in the battery needed for the sensor to work;
- `measures.{number}.battery.minVolts`: value representing the max volts the battery can hold;
- `measures.{number}.soilMoisture.relativePercentage`: value representing the percentage of water in the soil;
- `measures.{number}.illuminance.lux`: value representing luminous flux per unit area;
- `measures.{number}.trigger.value`: true or false / on or off;
- `measures.{number}.co2.ppm`: value representing parts per million of CO2 in the air;
- `measures.{number}.co.ppm`: value representing parts per million of CO2 in the air;
- `measures.{number}.voc.ppm`: value representing parts per million of VOC in the air;
- `measures.{number}.pm2_5.ppm`: value representing parts per million of PM2.5 in the air;
- `measures.{number}.pm10.ppm`: value representing parts per million of PM10 in the air;
- `measures.{number}.nh3.ppm`: value representing parts per million of NH3 in the air;
- `measures.{number}.o3.ppm`: value representing parts per million of O3 in the air;
- `measures.{number}.no2.ppm`: value representing parts per million of NO2 in the air;
- `measures.{number}.waterPressure.bar`: value representing pressure in water pipes in bar;
- `measures.{number}.ph.value`: value representing the pH level of water, between 0 and 14;
- `measures.{number}.occupation.percentage`: value representing the percentage of a volume that is occupied;
- `measures.{number}.soilConductivity.microSiemensPerCentimeter`: value representing the soil conductivity in micro siemens per centimeter;
- `measures.{number}.distance.millimeters`: value representing the distance to a given point in millimeters;
- `measures.{number}.distance.maxMillimeters`: value representing the maximum distance the sensor can be to a given point in millimeters;
- `measures.{number}.distance.minMillimeters`: value representing the minimum distance the sensor can be to a given point in millimeters;

Due to lack a of discussion and tests some data is missing a well-defined unit of measurement.

## Further Discussion

As always, changes/improvements to this data model are expected.
