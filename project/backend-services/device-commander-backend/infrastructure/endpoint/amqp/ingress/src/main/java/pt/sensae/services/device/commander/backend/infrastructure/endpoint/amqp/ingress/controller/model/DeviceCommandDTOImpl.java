package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.ingress.controller.model;

import pt.sensae.services.device.commander.backend.application.DeviceCommandDTO;

import java.util.UUID;

public class DeviceCommandDTOImpl implements DeviceCommandDTO {
    public UUID deviceId;
    public String commandId;
}
