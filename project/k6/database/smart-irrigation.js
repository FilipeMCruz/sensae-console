import sql from "k6/x/sql";
import http from "k6/http";

export function clearIrrigationData() {
  http.get(`http://${__ENV.SENSAE_INSTANCE_IP}:9098/exec?limit=0%2C1000&explain=true&count=true&src=con&query=TRUNCATE%20TABLE%20smart_irrigation_data&timings=true`);
  // irrigationDB.exec("TRUNCATE TABLE smart_irrigation_data;`);
}

export function initSmartIrrigationDatabase() {
  http.get(`http://${__ENV.SENSAE_INSTANCE_IP}:9098/exec?limit=0%2C1000&explain=true&count=true&src=con&query=CREATE+TABLE+IF+NOT+EXISTS+smart_irrigation_data%0A+%28%0A+data_id+SYMBOL+NOCACHE%2C%0A+device_id+SYMBOL%2C%0A+device_type+SYMBOL+capacity+3%2C%0A+reported_at+TIMESTAMP%2C%0A+payload_temperature+FLOAT%2C%0A+payload_humidity+FLOAT%2C%0A+payload_soil_moisture+FLOAT%2C%0A+payload_illuminance+FLOAT%2C%0A+payload_valve_status+boolean%0A+%29+timestamp%28reported_at%29%0A+PARTITION+BY+MONTH&timings=true`)
  // irrigationDB.exec(`CREATE TABLE IF NOT EXISTS smart_irrigation_data
  //   (
  //       data_id               SYMBOL NOCACHE,
  //       device_id             SYMBOL,
  //       device_type           SYMBOL capacity 3,
  //       reported_at           TIMESTAMP,
  //       payload_temperature   FLOAT,
  //       payload_humidity      FLOAT,
  //       payload_soil_moisture FLOAT,
  //       payload_illuminance   FLOAT,
  //       payload_valve_status  boolean
  //   ) timestamp(reported_at)
  //   PARTITION BY MONTH;`);
}

export function countSmartIrrigationMeasuresEntries() {
  const res = http.get(`http://${__ENV.SENSAE_INSTANCE_IP}:9098/exec?query=SELECT%20count%20FROM%20smart_irrigation_data`);
  return JSON.parse(res.body).dataset[0][0];
  // return irrigationDB.query("SELECT count FROM smart_irrigation_data");
}

// const irrigationDB = sql.open(
//   "postgres",
//   `postgres://admin:quest@${__ENV.SENSAE_INSTANCE_IP}:8898/qdb?sslmode=disable`
// );
