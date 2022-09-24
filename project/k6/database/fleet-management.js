import sql from "k6/x/sql";
import http from "k6/http";

export function clearFleetData() {
  http.get(
    `http://${__ENV.SENSAE_INSTANCE_IP}:9098/exec?limit=0%2C1000&explain=true&count=true&src=con&query=TRUNCATE%20TABLE%20data&timings=true`
  );
  // irrigationDB.exec("TRUNCATE TABLE data;`);
}

export function countFleetMeasuresEntries() {
  const res = http.get(
    `http://${__ENV.SENSAE_INSTANCE_IP}:9098/exec?query=SELECT%20count%20FROM%20data`
  );
  return JSON.parse(res.body).dataset[0][0];
  // return irrigationDB.query("SELECT count FROM data");
}

// const irrigationDB = sql.open(
//   "postgres",
//   `postgres://admin:quest@${__ENV.SENSAE_INSTANCE_IP}:8898/qdb?sslmode=disable`
// );
