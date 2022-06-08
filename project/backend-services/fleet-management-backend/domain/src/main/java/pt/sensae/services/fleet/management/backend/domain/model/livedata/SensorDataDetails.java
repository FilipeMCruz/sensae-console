package pt.sensae.services.fleet.management.backend.domain.model.livedata;

import pt.sensae.services.fleet.management.backend.domain.model.GPSDataDetails;

public record SensorDataDetails(GPSDataDetails gps, StatusDataDetails status) {
}
