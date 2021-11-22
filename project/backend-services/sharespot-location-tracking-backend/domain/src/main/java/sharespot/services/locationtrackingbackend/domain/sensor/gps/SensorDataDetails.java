package sharespot.services.locationtrackingbackend.domain.sensor.gps;

public record SensorDataDetails(GPSDataDetails gps) {
    @Override
    public String toString() {
        return "SensorDataDetails{" +
                "gps=" + gps +
                '}';
    }
}
