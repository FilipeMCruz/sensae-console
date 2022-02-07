package sharespot.services.fleetmanagementbackend.domain.model.livedata;

import java.util.UUID;

public record SensorData(UUID dataId, Device device, Long reportedAt,
                         SensorDataDetails data) {
}
