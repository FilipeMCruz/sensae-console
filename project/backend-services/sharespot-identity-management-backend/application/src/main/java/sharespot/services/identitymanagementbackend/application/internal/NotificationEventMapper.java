package sharespot.services.identitymanagementbackend.application.internal;

import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceWithAllOwnerDomains;

public interface NotificationEventMapper {

    DeviceNotificationDTO domainToUpdatedDto(DeviceWithAllOwnerDomains domain);

    DeviceId dtoToDomain(DeviceIdDTO dto);
}
