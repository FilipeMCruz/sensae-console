package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.device.commander.application.model.DeviceNotificationDTO;

@RegisterForReflection
public class DeviceNotificationDTOImpl implements DeviceNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceInformationDTOImpl information;
}
