package sharespot.services.identitymanagementbackend.application.internal.device;

import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceWithAllOwnerDomains;

public interface NotificationEventMapper {

    DeviceNotificationDTO domainToUpdatedDto(DeviceWithAllOwnerDomains domain);
}
