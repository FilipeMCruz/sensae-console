package pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.model;

import pt.sensae.services.device.management.slave.backend.application.DeviceNotificationDTO;

public class DeviceNotificationDTOImpl implements DeviceNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceInformationDTOImpl information;
}
