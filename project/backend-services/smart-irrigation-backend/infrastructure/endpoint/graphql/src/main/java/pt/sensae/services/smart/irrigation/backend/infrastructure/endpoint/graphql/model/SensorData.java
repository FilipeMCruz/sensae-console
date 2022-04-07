package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model;

import java.util.UUID;

public record SensorData(UUID dataId, Device device, Long reportedAt,
                         SensorDataDetails data) {
}
