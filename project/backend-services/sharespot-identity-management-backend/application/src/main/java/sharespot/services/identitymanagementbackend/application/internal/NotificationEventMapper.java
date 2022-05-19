package sharespot.services.identitymanagementbackend.application.internal;

import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceWithAllOwnerDomains;

public interface NotificationEventMapper {

    DeviceNotificationDTO domainToUpdatedDto(DeviceWithAllOwnerDomains domain);
}
