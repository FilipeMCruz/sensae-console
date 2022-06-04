package pt.sensae.services.device.management.master.backend.domain.model.staticData;

public enum StaticDataLabel {
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
    },
    GPS_ALTITUDE {
        @Override
        public String value() {
            return "gpsAltitude";
        }
    },
    BATTERY_MIN_VOLTS {
        @Override
        public String value() {
            return "batteryMinVolts";
        }
    },
    BATTERY_MAX_VOLTS {
        @Override
        public String value() {
            return "batteryMaxVolts";
        }
    },
    MIN_DISTANCE {
        @Override
        public String value() {
            return "minDistance";
        }
    },
    MAX_DISTANCE {
        @Override
        public String value() {
            return "maxDistance";
        }
    };

    public static StaticDataLabel give(String s) {
        if (GPS_LATITUDE.value().equals(s)) {
            return GPS_LATITUDE;
        } else if (GPS_LONGITUDE.value().equals(s)) {
            return GPS_LONGITUDE;
        } else if (GPS_ALTITUDE.value().equals(s)) {
            return GPS_ALTITUDE;
        } else if (BATTERY_MIN_VOLTS.value().equals(s)) {
            return BATTERY_MIN_VOLTS;
        } else if (BATTERY_MAX_VOLTS.value().equals(s)) {
            return BATTERY_MAX_VOLTS;
        } else if (MIN_DISTANCE.value().equals(s)) {
            return MIN_DISTANCE;
        }else if (MAX_DISTANCE.value().equals(s)) {
            return MAX_DISTANCE;
        }
        throw new IllegalArgumentException("No Enum specified for this string");
    }

    public abstract String value();
}
