package sharespot.services.locationtrackingbackend.domain.sensor.gps;

import java.util.Set;
import java.util.UUID;

public record SensorData(UUID dataId, UUID deviceId, Long reportedAt,
                         SensorDataDetails data, Set<RecordEntry> record) {
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
