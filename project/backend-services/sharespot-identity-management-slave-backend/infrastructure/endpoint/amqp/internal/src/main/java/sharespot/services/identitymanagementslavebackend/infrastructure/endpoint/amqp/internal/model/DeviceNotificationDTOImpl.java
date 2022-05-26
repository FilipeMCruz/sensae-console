package sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.model;

import sharespot.services.identitymanagementslavebackend.application.OwnershipNotificationDTO;

public class DeviceNotificationDTOImpl implements OwnershipNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceWithOwnershipDTOImpl information;
}
