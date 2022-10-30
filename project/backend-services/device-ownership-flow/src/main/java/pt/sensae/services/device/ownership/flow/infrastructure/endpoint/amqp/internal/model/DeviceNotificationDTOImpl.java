package pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.device.ownership.flow.application.model.OwnershipNotificationDTO;

@RegisterForReflection
public class DeviceNotificationDTOImpl implements OwnershipNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceWithOwnershipDTOImpl information;
}
