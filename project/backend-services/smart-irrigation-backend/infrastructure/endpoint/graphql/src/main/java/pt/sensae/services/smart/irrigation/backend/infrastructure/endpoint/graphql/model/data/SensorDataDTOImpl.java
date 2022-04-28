package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceDTOImpl;

import java.util.UUID;

public record SensorDataDTOImpl(UUID dataId, DeviceDTOImpl device, Long reportedAt,
                                SensorDataDetails data) implements SensorDataDTO {
}
