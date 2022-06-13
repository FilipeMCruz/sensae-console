package pt.sensae.services.fleet.management.backend.domain.model.pastdata;

import pt.sensae.services.fleet.management.backend.domain.model.GPSDataDetails;
import pt.sensae.services.fleet.management.backend.domain.model.livedata.StatusDataDetails;

public record GPSSensorDataHistoryStep(GPSDataDetails gps, StatusDataDetails status, Long reportedAt) {
}
