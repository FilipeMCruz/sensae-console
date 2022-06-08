package pt.sensae.services.identity.management.backend.application.internal.device;

import pt.sensae.services.identity.management.backend.domain.device.DeviceNotification;

public interface DeviceInformationMapper {

    DeviceNotification dtoToDomain(DeviceNotificationDTO dto);
}
