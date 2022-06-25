package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.device;

import pt.sensae.services.device.management.master.backend.application.DeviceNotificationDTO;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationTypeDTOImpl;

public class DeviceNotificationDTOImpl implements DeviceNotificationDTO {
    public String deviceId;

    public DeviceNotificationTypeDTOImpl type;

    public DeviceInformationDTOImpl information;

    public DeviceNotificationDTOImpl() {
    }
}
