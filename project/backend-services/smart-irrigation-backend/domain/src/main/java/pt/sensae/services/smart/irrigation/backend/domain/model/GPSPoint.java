package pt.sensae.services.smart.irrigation.backend.domain.model;

import pt.sharespot.iot.core.sensor.model.data.types.GPSDataDTO;

public record GPSPoint(Double latitude, Double longitude, Float altitude) {

    public static GPSPoint from(GPSDataDTO data) {
        return new GPSPoint(data.latitude, data.longitude, data.altitude);
    }

    public static GPSPoint ofLatLong(Double latitude, Double longitude) {
        return new GPSPoint(latitude, longitude, null);
    }

    public static GPSPoint ofLatLongAlt(Double latitude, Double longitude, Float altitude) {
        return new GPSPoint(latitude, longitude, altitude);
    }
}
