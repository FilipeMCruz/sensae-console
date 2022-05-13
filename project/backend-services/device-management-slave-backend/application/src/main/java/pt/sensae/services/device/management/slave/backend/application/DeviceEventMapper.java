package pt.sensae.services.device.management.slave.backend.application;

import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;
import pt.sensae.services.device.management.slave.backend.domain.model.notification.DeviceNotification;

public interface DeviceEventMapper {

    DeviceDTO domainToDto(Device domain);

    DeviceNotification dtoToDomain(DeviceNotificationDTO dto);

}
