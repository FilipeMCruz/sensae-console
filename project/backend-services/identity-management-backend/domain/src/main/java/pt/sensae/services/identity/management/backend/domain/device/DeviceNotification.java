package pt.sensae.services.identity.management.backend.domain.device;


import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;

public record DeviceNotification(DeviceId id, NotificationType type, DeviceInformation info) {
}
