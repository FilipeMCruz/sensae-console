package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.device.commander.application.model.DeviceDTO;

@RegisterForReflection
public class DeviceDTOImpl implements DeviceDTO {

    public String deviceId;

    public String name;

    public String downlink;
}
