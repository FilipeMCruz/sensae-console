package pt.sensae.services.device.ownership.backend.domain.notification;

import pt.sensae.services.device.ownership.backend.domain.DeviceWithAllPermissions;
import pt.sensae.services.device.ownership.backend.domain.device.DeviceId;

public record OwnershipNotification(DeviceId id, NotificationType type, DeviceWithAllPermissions info) {
}
