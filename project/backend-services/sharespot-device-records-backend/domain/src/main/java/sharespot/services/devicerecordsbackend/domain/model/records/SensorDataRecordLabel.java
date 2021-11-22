package sharespot.services.devicerecordsbackend.domain.model.records;

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

    public static SensorDataRecordLabel give(String value) {
        try {
            return SensorDataRecordLabel.valueOf(value);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public abstract String value();
}
