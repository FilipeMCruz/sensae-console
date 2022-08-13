package pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.device.ownership.flow.application.model.DeviceIdDTO;

@RegisterForReflection
public class DeviceIdDTOImpl implements DeviceIdDTO {
    public String id;
}
