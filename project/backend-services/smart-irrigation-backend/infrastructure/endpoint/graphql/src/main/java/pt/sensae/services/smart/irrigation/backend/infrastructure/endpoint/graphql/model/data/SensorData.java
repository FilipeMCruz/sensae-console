package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceDTOImpl;

import java.util.UUID;

public record SensorData(UUID dataId, DeviceDTOImpl device, Long reportedAt,
                         SensorDataDetails data) {
}
