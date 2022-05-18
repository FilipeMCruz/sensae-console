package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model;

import sharespot.services.identitymanagementbackend.application.internal.DeviceNotificationDTO;

public class DeviceNotificationDTOImpl implements DeviceNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceWithOwnershipDTOImpl information;
}
