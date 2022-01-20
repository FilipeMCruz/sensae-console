CREATE TABLE IF NOT EXISTS data(data_id SYMBOL, device_name SYMBOL, device_id SYMBOL, gps_data SYMBOL, reported_at DATE, ts TIMESTAMP)
    timestamp(ts)
PARTITION BY MONTH;
