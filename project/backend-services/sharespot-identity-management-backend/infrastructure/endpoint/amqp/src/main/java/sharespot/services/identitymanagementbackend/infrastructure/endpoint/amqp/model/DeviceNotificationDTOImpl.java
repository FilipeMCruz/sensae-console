package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model;

import sharespot.services.identitymanagementbackend.application.internal.device.DeviceNotificationDTO;

public class DeviceNotificationDTOImpl implements DeviceNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceWithOwnershipDTOImpl information;
}
