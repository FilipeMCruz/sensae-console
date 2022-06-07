package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.device;


import pt.sensae.services.identity.management.backend.application.internal.device.DeviceNotificationDTO;

public class DeviceNotificationDTOImpl implements DeviceNotificationDTO {

    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceInformationDTOImpl information;
}
