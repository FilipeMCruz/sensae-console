package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model;

import pt.sensae.services.device.management.master.backend.application.DeviceNotificationDTO;

public class DeviceNotificationDTOImpl implements DeviceNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceInformationDTOImpl information;
}
