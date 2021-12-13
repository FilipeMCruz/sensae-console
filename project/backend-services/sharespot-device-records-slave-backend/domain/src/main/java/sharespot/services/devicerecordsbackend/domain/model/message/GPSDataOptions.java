package sharespot.services.devicerecordsbackend.domain.model.message;

public enum GPSDataOptions{
    WITH_GPS_DATA,
    WITHOUT_GPS_DATA;

    public String value() {
        return this.name().toLowerCase();
    }
}
