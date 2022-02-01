package sharespot.services.locationtrackingbackend.domain.model.pastdata;

import sharespot.services.locationtrackingbackend.domain.model.GPSDataDetails;
import sharespot.services.locationtrackingbackend.domain.model.livedata.StatusDataDetails;

public record GPSSensorDataHistoryStep(GPSDataDetails gps, StatusDataDetails status, Long reportedAt) {
}
