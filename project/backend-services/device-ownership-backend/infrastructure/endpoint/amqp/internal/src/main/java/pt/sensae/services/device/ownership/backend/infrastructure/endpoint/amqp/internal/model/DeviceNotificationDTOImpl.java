package pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.internal.model;

import pt.sensae.services.device.ownership.backend.application.OwnershipNotificationDTO;

public class DeviceNotificationDTOImpl implements OwnershipNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceWithOwnershipDTOImpl information;
}
