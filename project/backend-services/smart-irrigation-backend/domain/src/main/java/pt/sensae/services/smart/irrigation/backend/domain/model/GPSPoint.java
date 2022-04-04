package pt.sensae.services.smart.irrigation.backend.domain.model;

public class GPSPoint {

    private Float latitude;
    private Float longitude;
    private Float altitude;

    private GPSPoint() {

    }

    public static GPSPoint ofLatLong(Float latitude, Float longitude) {
        var gpsDataDTO = new GPSPoint();
        gpsDataDTO.latitude = latitude;
        gpsDataDTO.longitude = longitude;
        return gpsDataDTO;
    }

    public static GPSPoint ofLatLongAlt(Float latitude, Float longitude, Float altitude) {
        var gpsDataDTO = new GPSPoint();
        gpsDataDTO.latitude = latitude;
        gpsDataDTO.longitude = longitude;
        gpsDataDTO.altitude = altitude;
        return gpsDataDTO;
    }
}
