package sharespot.services.locationtrackingbackend.domain.model.livedata;

import sharespot.services.locationtrackingbackend.domain.model.GPSDataDetails;

public record SensorDataDetails(GPSDataDetails gps, StatusDataDetails status) {
}
