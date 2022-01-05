package sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.model.livedata;

import sharespot.services.locationtrackingbackend.application.OutSensorData;

import java.util.UUID;

public record SensorData(UUID dataId, Device device, Long reportedAt,
                         SensorDataDetails data) implements OutSensorData {
}
