package sharespot.services.locationtrackingbackend.domain.sensor.gps;

import java.util.Set;
import java.util.UUID;

public record SensorData(UUID dataId, Sensor device, Long reportedAt,
                         SensorDataDetails data, Set<RecordEntry> record) {
}
