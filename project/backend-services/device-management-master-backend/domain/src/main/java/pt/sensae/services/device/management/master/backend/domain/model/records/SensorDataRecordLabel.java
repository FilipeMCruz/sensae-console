package pt.sensae.services.device.management.master.backend.domain.model.records;

public enum SensorDataRecordLabel {
    GPS_LATITUDE {
        @Override
        public String value() {
            return "gpsLatitude";
        }
    },
    GPS_LONGITUDE {
        @Override
        public String value() {
            return "gpsLongitude";
        }
    };

    public static SensorDataRecordLabel give(String s) {
        if (GPS_LATITUDE.value().equals(s)) {
            return GPS_LATITUDE;
        } else if (GPS_LONGITUDE.value().equals(s)) {
            return GPS_LONGITUDE;
        }
        throw new IllegalArgumentException("No Enum specified for this string");
    }

    public abstract String value();
}
