# Model

This document describes the latest version of the data model used in the system.

Current version:

- `iot-core` : `0.1.20`
- `system` : `0.10.0`

## Introduction

This model comes into play once the IoT sensor/controller data is processed, either by a `Data Processor Slave` or by a `Data Decoder Slave` container.

Containers such as `Device Management Slave`, `Identity Management`, `Data Validator`, `Fleet Management Backend`, `Smart Irrigation Backend` and `Alert Dispatcher` expect this specific data structure and semantics.

This model is described by the [iot-core](https://github.com/ShareSpotPT/iot-core) package.

## Data Model

The current data model is represented here as a Json Schema.
Internally data is handled using [protobuf](https://github.com/protocolbuffers/protobuf).

``` json
{
  "dataId": "[uuid]",
  "reportedAt": "[long]",
  "device": {
    "id": "[uuid]",
    "name": "[string]",
    "downlink": "[string]",
    "records": [
      {
        "label": "[string]",
        "content": "[string]"
      }
    ],
    "domains":  ["[uuid]"],
    "commands": {
      "[int]": [
        {
          "id": "[uuid]",
          "name": "[string]",
          "payload": "[base64 string]",
          "port": "[int]"
        }
      ]
    }
  },
  "measures": {
    "[int]": {
      "airHumidity": {
        "gramsPerCubicMeter": "[float]",
        "relativePercentage": "[float]"
      },
      "airPressure": {
        "hectoPascal": "[float]"
      },
      "aqi": {
        "value": "[float]"
      },
      "battery": {
        "percentage": "[float]",
        "volts": "[float]",
        "maxVolts": "[float]",
        "minVolts": "[float]"
      },
      "co2": {
        "ppm": "[float]"
      },
      "co": {
        "ppm": "[float]"
      },
      "distance": {
        "millimeters": "[float]",
        "maxMillimeters": "[float]",
        "minMillimeters": "[float]"
      },
      "gps": {
        "latitude": "[double]",
        "longitude": "[double]",
        "altitude": "[float]"
      },
      "illuminance": {
        "lux": "[float]"
      },
      "motion": {
        "value": "[ACTIVE, INACTIVE or UNKNOWN]"
      },
      "nh3": {
        "ppm": "[float]"
      },
      "no2": {
        "ppm": "[float]"
      },
      "o3": {
        "ppm": "[float]"
      },
      "occupation": {
        "percentage": "[float]"
      },
      "ph": {
        "value": "[float]"
      },
      "pm2_5": {
        "microGramsPerCubicMeter": "[float]"
      },
      "pm10": {
        "microGramsPerCubicMeter": "[float]"
      },
      "soilConductivity": {
        "microSiemensPerCentimeter": "[float]"
      },
      "soilMoisture": {
        "relativePercentage": "[float]"
      },
      "temperature": {
        "celsius": "[float]"
      },
      "trigger": {
        "value": "[boolean]"
      },
      "velocity": {
        "kilometerPerHour": "[float]"
      },
      "voc": {
        "ppm": "[float]"
      },
      "waterPressure": {
        "bar": "[float]"
      }
    }
  }
}
```

At the time of data processing, though `Data Processor Slave` or `Data Decoder Slave` containers, each property can be required, optional or rejected (if written).

``` json
{
  "dataId": "[required]",
  "reportedAt": "[required]",
  "device": {
    "id": "[required]",
    "name": "[optional]",
    "downlink": "[optional]",
    "records": [
      {
        "label": "[optional]",
        "content": "[optional]"
      }
    ],
    "domains": ["[rejected]"],
    "commands": {
      "[optional]": [
        {
          "id": "[optional]",
          "name": "[optional]",
          "payload": "[optional]",
          "port": "[optional]"
        }
      ]
    }
  },
  "measures": {
    "[optional]": {
      "airHumidity": {
        "gramsPerCubicMeter": "[optional]",
        "relativePercentage": "[optional]"
      },
      "airPressure": {
        "hectoPascal": "[optional]"
      },
      "aqi": {
        "value": "[optional]"
      },
      "battery": {
        "percentage": "[optional]",
        "volts": "[optional]",
        "maxVolts": "[optional]",
        "minVolts": "[optional]"
      },
      "co2": {
        "ppm": "[optional]"
      },
      "co": {
        "ppm": "[optional]"
      },
      "distance": {
        "millimeters": "[optional]",
        "maxMillimeters": "[optional]",
        "minMillimeters": "[optional]"
      },
      "gps": {
        "latitude": "[optional]",
        "longitude": "[optional]",
        "altitude": "[optional]"
      },
      "illuminance": {
        "lux": "[optional]"
      },
      "motion": {
        "value": "[optional]"
      },
      "nh3": {
        "ppm": "[optional]"
      },
      "no2": {
        "ppm": "[optional]"
      },
      "o3": {
        "ppm": "[optional]"
      },
      "occupation": {
        "percentage": "[optional]"
      },
      "ph": {
        "value": "[optional]"
      },
      "pm2_5": {
        "microGramsPerCubicMeter": "[optional]"
      },
      "pm10": {
        "microGramsPerCubicMeter": "[optional]"
      },
      "soilConductivity": {
        "microSiemensPerCentimeter": "[optional]"
      },
      "soilMoisture": {
        "relativePercentage": "[optional]"
      },
      "temperature": {
        "celsius": "[optional]"
      },
      "trigger": {
        "value": "[optional]"
      },
      "velocity": {
        "kilometerPerHour": "[optional]"
      },
      "voc": {
        "ppm": "[optional]"
      },
      "waterPressure": {
        "bar": "[optional]"
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
- `device.commands.{number}.payload`: base64 string;

Due to a lack of discussion and tests some data types may be missing a well-defined unit of measurement.

## Support for Controllers

In order to support Controllers, devices that aggregate various sensors, `measures` and `device.commands` can link to a sub device.

The value **0** is reserved for the controller/main device, any other number can be used to reference a sub device.

As an example imagine a controller that has 3 sub devices, a valve and two sensors, one with temperature values and other with humidity values.
We could map the `valve` to sub device **1**, the `temperature sensor` to sub device **2** and the `humidity sensor` to sub device **3**.

In `device management` this controller would be defined as a device that holds 3 devices, and would reference device 'XXX' as sub device **1** (the `valve`), device 'YYY' (the `temperature sensor`) as sub device **2** and device 'ZZZ' (the `humidity sensor`) as sub device **3**.

Everything in `measures.{number}` and `device.commands.{number}` would be processed as if it was sent by the referenced sub device and not the controller.

## Further Discussion

As always, changes/improvements to this data model are expected.
