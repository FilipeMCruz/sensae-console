package sharespot.services.identitymanagementslavebackend.application;

import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.notification.OwnershipNotification;

public interface DeviceNotificationEventMapper {

    DeviceIdDTO domainToDto(DeviceId domain);

    OwnershipNotification dtoToDomain(OwnershipNotificationDTO dto);

}
