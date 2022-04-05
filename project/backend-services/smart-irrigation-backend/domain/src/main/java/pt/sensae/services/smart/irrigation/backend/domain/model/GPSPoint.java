package pt.sensae.services.smart.irrigation.backend.domain.model;

import pt.sharespot.iot.core.sensor.data.GPSDataDTO;

public record GPSPoint(Float latitude, Float longitude, Float altitude) {

    public static GPSPoint from(GPSDataDTO data) {
        return new GPSPoint(data.latitude.floatValue(), data.longitude.floatValue(), data.altitude.floatValue());
    }

    public static GPSPoint ofLatLong(Float latitude, Float longitude) {
        return new GPSPoint(latitude, longitude, null);
    }

    public static GPSPoint ofLatLongAlt(Float latitude, Float longitude, Float altitude) {
        return new GPSPoint(latitude, longitude, altitude);
    }
}
