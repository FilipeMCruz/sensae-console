CREATE TABLE data2(data_id SYMBOL NOCACHE, device_name SYMBOL, device_id SYMBOL, gps_data SYMBOL NOCACHE, reported_at DATE, ts TIMESTAMP, motion SYMBOL capacity 3, domain SYMBOL index)
    timestamp(ts)
             PARTITION BY MONTH;
