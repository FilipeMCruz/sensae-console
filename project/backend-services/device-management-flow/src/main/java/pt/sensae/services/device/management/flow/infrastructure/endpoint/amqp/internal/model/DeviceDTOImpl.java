package pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.device.management.flow.application.model.DeviceDTO;

@RegisterForReflection
public class DeviceDTOImpl implements DeviceDTO {

    public String deviceId;

    public String name;

    public String downlink;
}
