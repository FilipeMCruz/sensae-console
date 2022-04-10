package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.application.model.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.Device;

import java.util.UUID;

public record SensorDataDTOImpl(UUID dataId, Device device, Long reportedAt,
                                SensorDataDetails data) implements SensorDataDTO {
}
