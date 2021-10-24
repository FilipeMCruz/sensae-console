package pt.sharespot.services.locationtrackingbackend.domain.sensor.gps;

import pt.sharespot.services.locationtrackingbackend.domain.exceptions.NotValidException;

public record GPSDataDetails(Double latitude, Double longitude) {

    public GPSDataDetails {
        if(latitude > 90 || latitude < -90) throw new NotValidException("Latitude must have a value between 90 and -90");

        if(longitude > 180 || longitude < -180) throw new NotValidException("Longitude must have a value between 180 and -180");
    }
}
