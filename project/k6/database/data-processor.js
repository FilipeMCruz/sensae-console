import sql from "k6/x/sql";

export function createEM300THProcessor() {
  processorDB.exec(`DO $$
    DECLARE id integer;
    BEGIN
    INSERT INTO public.transformation (device_type)
    VALUES ('em300th') RETURNING persistence_id INTO id;
    
    INSERT INTO public.property_transformation (value, old_path, transformation_persistence_id, sub_sensor_id)
    VALUES (7,'temperature',id,0), (19,'humidity',id,0), (1,'id',id,0), (2,'name',id,0), (4,'reported_at',id,0), (0,'uuid',id,0);
    END $$;`);
}

export function clearProcessors() {
  processorDB.exec("TRUNCATE public.transformation CASCADE;");
}

const processorDB = sql.open(
  "postgres",
  `postgres://user:${__ENV.SENSAE_COMMON_DATABASE_PASSWORD}@localhost:5482/processor?sslmode=disable`
);
