import sql from "k6/x/sql";

export function insertDevice(device) {
  if (device.lat && device.long) {
    deviceDB.exec(`DO $$
      DECLARE id integer;
      BEGIN
      INSERT INTO public.record (device_id, name, downlink)
      VALUES ('${device.id}', '${device.name}', '') RETURNING persistence_id INTO id;
      
      INSERT INTO public.record_entry (content, label, type, records_persistence_id)
      VALUES ('${device.lat}', 'gpsLatitude', 1, id);
      
      INSERT INTO public.record_entry (content, label, type, records_persistence_id)
      VALUES ('${device.long}', 'gpsLongitude', 1, id);
      
      INSERT INTO public.record_entry (content, label, type, records_persistence_id)
      VALUES ('perf', 'Project', 0, id);
      END $$;`);
  } else {
    deviceDB.exec(`DO $$
      DECLARE id integer;
      BEGIN
      INSERT INTO public.record (device_id, name, downlink)
      VALUES ('${device.id}', '${device.name}', '') RETURNING persistence_id INTO id;
      
      INSERT INTO public.record_entry (content, label, type, records_persistence_id)
      VALUES ('perf', 'Project', 0, id);
      END $$;`);
  }
}

export function clearDevices() {
  deviceDB.exec("TRUNCATE public.record CASCADE;");
  deviceDB.close();
}

const deviceDB = sql.open(
  "postgres",
  `postgres://user:${__ENV.SENSAE_COMMON_DATABASE_PASSWORD}@localhost:5488/device?sslmode=disable`
);
