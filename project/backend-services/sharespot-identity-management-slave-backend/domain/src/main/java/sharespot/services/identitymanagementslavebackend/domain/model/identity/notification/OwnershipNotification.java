package sharespot.services.identitymanagementslavebackend.domain.model.identity.notification;

import sharespot.services.identitymanagementslavebackend.domain.model.identity.DeviceWithAllPermissions;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;

public record OwnershipNotification(DeviceId id, NotificationType type, DeviceWithAllPermissions info) {
}
