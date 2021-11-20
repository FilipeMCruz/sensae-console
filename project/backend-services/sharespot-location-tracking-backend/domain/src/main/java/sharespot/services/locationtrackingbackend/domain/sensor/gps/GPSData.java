package sharespot.services.locationtrackingbackend.domain.sensor.gps;

import java.util.UUID;

public record GPSData(UUID dataId, UUID deviceId, Long reportedAt,
                      GPSDataDetails data) {
    @Override
    public String toString() {
        return "GPSData{" +
                "dataId=" + dataId +
                ", deviceId=" + deviceId +
                ", reportedAt=" + reportedAt +
                ", data=" + data +
                '}';
    }
}
