package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.smart.irrigation.backend.application.model.SensorDataDTO;

import java.util.UUID;

public record SensorDataDTOImpl(UUID dataId, Device device, Long reportedAt,
                                SensorDataDetails data) implements SensorDataDTO {
}
