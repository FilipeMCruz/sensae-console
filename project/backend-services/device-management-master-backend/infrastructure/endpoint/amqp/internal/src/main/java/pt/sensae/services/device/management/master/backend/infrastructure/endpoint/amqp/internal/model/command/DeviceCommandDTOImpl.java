package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.command;

import pt.sensae.services.device.management.master.backend.application.command.DeviceCommandDTO;

import java.util.UUID;

public class DeviceCommandDTOImpl implements DeviceCommandDTO {
    public UUID deviceId;
    public String commandId;
}
