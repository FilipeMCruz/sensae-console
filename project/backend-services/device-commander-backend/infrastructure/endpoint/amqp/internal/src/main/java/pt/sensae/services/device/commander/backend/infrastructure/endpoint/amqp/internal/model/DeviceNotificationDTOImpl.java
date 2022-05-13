package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.model;

import pt.sensae.services.device.commander.backend.application.DeviceNotificationDTO;

public class DeviceNotificationDTOImpl implements DeviceNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceInformationDTOImpl information;
}
