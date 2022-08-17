package pt.sensae.services.device.management.flow.application.mapper;

import pt.sensae.services.device.management.flow.application.model.DeviceDTO;
import pt.sensae.services.device.management.flow.application.model.DeviceNotificationDTO;
import pt.sensae.services.device.management.flow.domain.device.Device;
import pt.sensae.services.device.management.flow.domain.notification.DeviceNotification;

public interface DeviceEventMapper {

    DeviceDTO domainToDto(Device domain);

    DeviceNotification dtoToDomain(DeviceNotificationDTO dto);

}
