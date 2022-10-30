package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.ingress.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.device.commander.application.model.DeviceCommandDTO;

import java.util.UUID;

@RegisterForReflection
public class DeviceCommandDTOImpl implements DeviceCommandDTO {
    public UUID deviceId;
    public String commandId;
}
