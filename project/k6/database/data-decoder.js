import sql from "k6/x/sql";

export function createEM300THDecoder() {
  decoderDB.exec(`INSERT INTO public.decoder (device_type, script)
    VALUES ('em300th', 'function convert(object) {
      return {
        dataId: object.uuid,
        reportedAt: object.reported_at,
        device: {
          id: object.id,
          name: object.name,
        },
        measures: {
          0: {
            temperature: {
              celsius: object.temperature,
            },
            airHumidity: {
              relativePercentage: object.humidity,
            },
          },
        },
      };
    }');`);
}

export function clearDecoders() {
  decoderDB.exec("TRUNCATE public.decoder CASCADE;");
  decoderDB.close();
}

const decoderDB = sql.open(
  "postgres",
  `postgres://user:${__ENV.SENSAE_COMMON_DATABASE_PASSWORD}@${__ENV.SENSAE_INSTANCE_IP}:5484/decoder?sslmode=disable`
);
