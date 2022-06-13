package pt.sensae.services.device.ownership.backend.application;

import pt.sensae.services.device.ownership.backend.domain.device.DeviceId;
import pt.sensae.services.device.ownership.backend.domain.notification.OwnershipNotification;

public interface DeviceNotificationEventMapper {

    DeviceIdDTO domainToDto(DeviceId domain);

    OwnershipNotification dtoToDomain(OwnershipNotificationDTO dto);

}
