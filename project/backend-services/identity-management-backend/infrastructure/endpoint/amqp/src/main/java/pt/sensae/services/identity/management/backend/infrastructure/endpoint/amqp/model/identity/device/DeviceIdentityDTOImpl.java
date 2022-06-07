package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.identity.device;

import pt.sensae.services.identity.management.backend.application.internal.identity.device.DeviceIdentityDTO;

public class DeviceIdentityDTOImpl implements DeviceIdentityDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceWithOwnershipDTOImpl information;
}
