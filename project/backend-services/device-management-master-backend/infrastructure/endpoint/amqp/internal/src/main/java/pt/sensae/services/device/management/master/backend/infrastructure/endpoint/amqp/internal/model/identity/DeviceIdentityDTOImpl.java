package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.identity;

import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationTypeDTOImpl;

public class DeviceIdentityDTOImpl {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceIdentityWithOwnershipDTOImpl information;
}
