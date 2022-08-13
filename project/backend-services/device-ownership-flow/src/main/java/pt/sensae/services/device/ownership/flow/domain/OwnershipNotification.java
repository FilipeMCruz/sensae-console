package pt.sensae.services.device.ownership.flow.domain;

public record OwnershipNotification(DeviceId id, NotificationType type, DeviceWithAllPermissions info) {
}
