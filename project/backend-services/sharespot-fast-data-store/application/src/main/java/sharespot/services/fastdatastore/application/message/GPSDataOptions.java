package sharespot.services.fastdatastore.application.message;

public enum GPSDataOptions{
    WITH_GPS_DATA,
    WITHOUT_GPS_DATA;

    public String value() {
        return this.name().toLowerCase();
    }
}
