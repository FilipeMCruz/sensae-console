package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model;

import pt.sensae.services.identity.management.backend.application.internal.device.DeviceIdentityDTO;

public class DeviceIdentityDTOImpl implements DeviceIdentityDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceWithOwnershipDTOImpl information;
}
