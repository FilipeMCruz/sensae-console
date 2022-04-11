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
        "gps": {
            "latitude": [double],
            "longitude": [double],
            "altitude": [double]
        },
        "temperature": {
            "celsius": [double]
        },
        "motion": {
            "value": [enum]
        },
        "velocity": {
            "kmperh": [double]
        },
        "aqi": {
            "eaqi": [double]
        },
        "humidity": {
            "gramspercubicmeter": [double]
        },
        "pressure": {
            "hPa": [double]
        },
        "battery": {
            "volts": [double],
            "percentage": [double],
        },
        "moisture": {
            "percentage": [double]
        },
        "illuminance": {
            "lux": [double]
        },
        "alarm": {
            "value": [boolean]
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
            "longitude": [optional],
            "altitude": [Optional]
        },
        "temperature": {
            "celsius": [optional]
        },
        "motion": {
            "value": [optional]
        },
        "velocity": {
            "kmperh": [optional]
        },
        "aqi": {
            "eaqi": [optional]
        },
        "humidity": {
            "gramspercubicmeter": [optional]
        },
        "pressure": {
            "hPa": [optional]
        },
        "battery": {
            "volts": [optional],
            "percentage": [optional],
        },
        "moisture": {
            "percentage": [optional]
        },
        "illuminance": {
            "lux": [optional]
        },
        "alarm": {
            "value": [optional]
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
- `data.velocity.kmperh`: value in km/h;
- `data.temperature.celsius`: value in celsius;
- `data.aqi.eaqi`: value in EAQI [ref](https://airindex.eea.europa.eu/Map/AQI/Viewer/);
- `data.humidity.gramspercubicmeter`: value with grams of water per cubic meter;
- `data.pressure.hPa`: value in hPa;
- `data.battery.volts`: value in volts;
- `data.battery.percentage`: value representing the percentage of battery still available;
- `data.moisture.percentage`: value representing the percentage of water in the soil;
- `data.illuminance.lux`: value representing luminous flux per unit area;
- `data.alarm.value`: true or false / on or off;

Due to lack a of discussion and tests some data is missing a well-defined unit of measurement.

## Improvements

- Add `data.air_humidity.relative_percentage` as percentage
- Add `data.water_pressure.bar` for water pipes
- Add `data.battery.max_volts`
- Add `data.battery.min_volts`
- Add `data.ph`
- Add `data.distance.millimeters`
- Add `data.distance.max_millimeters`
- Add `data.distance.min_millimeters`
- Add `data.occupation.percentage`
- Add `data.soil_conductivity.micro_siemens_per_cm`
- Change `data.moisture.percentage` to `data.soil_moisture.relative_percentage`
- Add `data.aqi.co2.ppm`
- Add `data.aqi.co2.micro_grams_per_cubic_meter`
- Add `data.aqi.co.ppm`
- Add `data.aqi.co.micro_grams_per_cubic_meter`
- Add `data.aqi.no2.ppm`
- Add `data.aqi.no2.micro_grams_per_cubic_meter`
- Add `data.aqi.nh3.ppm`
- Add `data.aqi.nh3.micro_grams_per_cubic_meter`
- Add `data.aqi.o3.ppm`
- Add `data.aqi.o3.micro_grams_per_cubic_meter`
- Add `data.aqi.pm2_5.micro_grams_per_cubic_meter`
- Add `data.aqi.pm10.micro_grams_per_cubic_meter`
- Change `data.pressure.hPa` to `data.air_pressure.hecto_pascal` air pressure
- Change `data.airHumidity.gramspercubicmeter` to `data.air_humidity.grams_per_cubic_meter`
- Change `data.velocity.kmperh` to `data.velocity.kilometer_per_hour`
- Change `data.alarm.value` to `data.alert.value`

Maybe alow data to be inside an array of `measures`.

If only one is provided then the `device_id` is the one from the `device_id`, if not each measure is assigned a new `virtual_device_key`.

The message would need to be splited for each "measure" and assigned to a virtual device in a new container.

This container would store a device_id, and an array of `virtual_device_id` + `virtual_device_key`, split the data and then send multiple messages.

The routing keys would need a `measures` key with `multi` or `single`. After this container the info would be sent to `data_validator` and follow the usual
route.

## Further Discussion

As always, changes/improvements to this data model are expected.
