package pt.sensae.services.device.commander.application.mapper;

import pt.sensae.services.device.commander.application.model.DeviceDTO;
import pt.sensae.services.device.commander.application.model.DeviceNotificationDTO;
import pt.sensae.services.device.commander.domain.device.Device;
import pt.sensae.services.device.commander.domain.notification.DeviceNotification;

public interface DeviceEventMapper {

    DeviceDTO domainToDto(Device domain);

    DeviceNotification dtoToDomain(DeviceNotificationDTO dto);

}
