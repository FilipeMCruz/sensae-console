package sharespot.services.devicerecordsbackend.domain.model.sensors;

public class SensorDataDetails {

    private final GPS gps;

    public SensorDataDetails(GPS gps) {
        this.gps = gps;
    }

    public GPS getGps() {
        return gps;
    }

    public static class GPS {

        private final Double latitude;
        private final Double longitude;

        public GPS(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Double latitude() {
            return latitude;
        }

        public Double longitude() {
            return longitude;
        }
    }
}
