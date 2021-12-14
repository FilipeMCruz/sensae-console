package sharespot.services.datastore.application.model;

public enum GPSDataOptions{
    WITH_GPS_DATA,
    WITHOUT_GPS_DATA;

    public String value() {
        return this.name().toLowerCase();
    }
}
