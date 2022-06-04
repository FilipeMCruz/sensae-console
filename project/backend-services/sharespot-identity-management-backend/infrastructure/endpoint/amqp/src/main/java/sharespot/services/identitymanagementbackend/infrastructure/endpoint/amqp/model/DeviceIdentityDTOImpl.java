package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model;

import sharespot.services.identitymanagementbackend.application.internal.device.DeviceIdentityDTO;

public class DeviceIdentityDTOImpl implements DeviceIdentityDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceWithOwnershipDTOImpl information;
}
