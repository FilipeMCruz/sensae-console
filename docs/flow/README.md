# Data Flow

This section represents how data flows according to the latest version of the system.

Current version:

- `system` : `0.8.0`
- `iot-core` : `0.1.17`

## Description

This system leverages RabbitMQ functionalities to route data though it.

Each container subscribes to specific Routing Keys, currently these are the available ones:

- **Container Type**: e.g. Data Gateway;
- **`iot-core` Version**: used by the container (to ensure compatibility across the system);
- **data**;
- **Sensor Data Information Type**: `encoded`, `decoded` or `processed`
- **Sensor Type**: e.g. `lgt92`;
- **Channel**: a string representing a _Channel_ of data, **default** is used when none is provided.
- **Ownership**: related to domain ownership;
- **Records**: related to device records, if it contains or not records;
- **Legitimacy**: if the main sensor measures received are valid, invalid, undetermined or unknown;
- **GPS Data**: if the main sensor measures contains GPS Data;
- **Temperature Data**: if the main sensor measures contains Temperature Data;
- **AQI Data**: if the main sensor measures contains AQI Data;
- **Air Humidity Data**: if the main sensor measures contains Air Humidity Data;
- **Motion Status Data**: if the main sensor measures contains Motion Status Data;
- **Velocity Data**: if the main sensor measures contains Velocity Data;
- **Air Pressure Data**: if the main sensor measures contains Air Pressure Data;
- **Soil Moisture Data**: if the main sensor measures contains Soil Moisture Data;
- **Illuminance Data**: if the main sensor measures contains Illuminance Data;
- **Trigger Data**: if the main sensor measures contains Trigger Data;
- **Battery Data**: if the main sensor measures contains Battery Data;
- **Water Pressure Data**: if the main sensor measures contains Water Pressure Data;
- **PH Data**: if the main sensor measures contains pH Data;
- **Distance Data**: if the main sensor measures contains Distance Data;
- **Occupation Data**: if the main sensor measures contains Occupation Data;
- **Soil Conductivity Data**: if the main sensor measures contains Soil Conductivity Data;
- **CO2 Data**: if the main sensor measures contains CO2 Data;
- **CO Data**: if the main sensor measures contains CO Data;
- **NH3 Data**: if the main sensor measures contains NH3 Data;
- **NO2 Data**: if the main sensor measures contains NO2 Data;
- **O3 Data**: if the main sensor measures contains O3 Data;
- **VOC Data**: if the main sensor measures contains VOC Data;
- **PM 2.5 Data**: if the main sensor measures contains PM 2.5 Data;
- **PM 10 Data**: if the main sensor measures contains PM 10 Data;

This properties are further explained in the [model](../model/README.md) section.

Each sensor data received is wrapped in a `Message` with an unique id to prevent re processing data due to unexpected routing paths, a Time to Live parameter set to 10 that decreases as the data packet moves thought the containers and the routing keys used to route the message.

As an example this are the routing key of a sensor data that was accepted by `fleet management`:

`device_management.0.1.17.data.processed.lgt92.default.with_domain_ownership.with_records.correct.with_gps_data.without_temperature_data.without_aqi_data.without_air_humidity_data.without_motion_data.without_velocity_data.without_air_pressure_data.without_soil_moisture_data.without_illuminance_data.without_trigger_data.without_battery_data.without_water_pressure_data.without_ph_data.without_distance_data.without_occupation_data.without_soil_conductivity_data.without_co2_data.without_co_data.without_nh3_data.without_no2_data.without_o3_data.without_voc_data.without_pm2_5_data.without_pm10_data.#`

On a Higher level sensor data flows in the system as represented in here:

![data-flow](diagrams/data-flow.svg)

This diagram does not account for invalid data or possible errors that occur during the process.

## Further Discussion

As always, changes/improvements to this page and the data flow are expected.
