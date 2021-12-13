package sharespot.services.locationtrackingbackend.domain.message;

public enum GPSDataOptions{
    WITH_GPS_DATA,
    WITHOUT_GPS_DATA;

    public String value() {
        return this.name().toLowerCase();
    }
}
