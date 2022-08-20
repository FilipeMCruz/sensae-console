CREATE TABLE IF NOT EXISTS smart_irrigation_data
(
    data_id               SYMBOL NOCACHE,
    device_id             SYMBOL,
    device_type           SYMBOL capacity 3,
    reported_at           TIMESTAMP,
    payload_temperature   FLOAT,
    payload_humidity      FLOAT,
    payload_soil_moisture FLOAT,
    payload_illuminance   FLOAT,
    payload_valve_status  boolean
) timestamp(reported_at)
PARTITION BY MONTH;
