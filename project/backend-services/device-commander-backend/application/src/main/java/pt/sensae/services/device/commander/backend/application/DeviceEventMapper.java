package pt.sensae.services.device.commander.backend.application;

import pt.sensae.services.device.commander.backend.domain.model.device.Device;
import pt.sensae.services.device.commander.backend.domain.model.notification.DeviceNotification;

public interface DeviceEventMapper {

    DeviceDTO domainToDto(Device domain);

    DeviceNotification dtoToDomain(DeviceNotificationDTO dto);

}
