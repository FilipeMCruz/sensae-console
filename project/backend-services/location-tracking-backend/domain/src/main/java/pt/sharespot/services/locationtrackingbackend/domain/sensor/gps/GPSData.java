package pt.sharespot.services.locationtrackingbackend.domain.sensor.gps;

import java.util.UUID;

public record GPSData(UUID dataId, UUID sensorId, Long reportedAt,
                      GPSDataDetails data) {

}
