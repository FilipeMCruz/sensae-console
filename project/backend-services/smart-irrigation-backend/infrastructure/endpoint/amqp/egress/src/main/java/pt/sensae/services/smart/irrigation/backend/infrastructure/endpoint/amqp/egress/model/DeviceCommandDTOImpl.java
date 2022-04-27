package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.egress.model;

import pt.sensae.services.smart.irrigation.backend.application.model.command.DeviceCommandDTO;

import java.util.UUID;

public class DeviceCommandDTOImpl implements DeviceCommandDTO {
    public UUID deviceId;
    public String commandId;
}
