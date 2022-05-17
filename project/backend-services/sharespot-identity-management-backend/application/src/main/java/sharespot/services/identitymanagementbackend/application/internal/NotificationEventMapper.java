package sharespot.services.identitymanagementbackend.application.internal;

import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceWithAllPermissions;

public interface NotificationEventMapper {

    DeviceNotificationDTO domainToUpdatedDto(DeviceWithAllPermissions domain);

    DeviceId dtoToDomain(DeviceIdDTO dto);
}
