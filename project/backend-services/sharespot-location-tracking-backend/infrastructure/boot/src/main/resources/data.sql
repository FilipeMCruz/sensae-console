CREATE TABLE IF NOT EXISTS location_tracking_data(data_id SYMBOL, device_name SYMBOL, device_id SYMBOL, gps_data GEOHASH(60b), reported_at DATE, ts TIMESTAMP)
    timestamp(ts)
PARTITION BY MONTH;
