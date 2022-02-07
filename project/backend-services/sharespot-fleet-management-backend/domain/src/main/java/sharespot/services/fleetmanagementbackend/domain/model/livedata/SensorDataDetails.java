package sharespot.services.fleetmanagementbackend.domain.model.livedata;

import sharespot.services.fleetmanagementbackend.domain.model.GPSDataDetails;

public record SensorDataDetails(GPSDataDetails gps, StatusDataDetails status) {
}
