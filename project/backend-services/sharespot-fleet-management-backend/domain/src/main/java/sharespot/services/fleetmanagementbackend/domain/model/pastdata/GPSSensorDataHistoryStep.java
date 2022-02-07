package sharespot.services.fleetmanagementbackend.domain.model.pastdata;

import sharespot.services.fleetmanagementbackend.domain.model.GPSDataDetails;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.StatusDataDetails;

public record GPSSensorDataHistoryStep(GPSDataDetails gps, StatusDataDetails status, Long reportedAt) {
}
