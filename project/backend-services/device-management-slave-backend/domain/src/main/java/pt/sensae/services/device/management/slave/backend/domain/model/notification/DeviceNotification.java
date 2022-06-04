package pt.sensae.services.device.management.slave.backend.domain.model.notification;

import pt.sensae.services.device.management.slave.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;

public record DeviceNotification(DeviceId id, NotificationType type, DeviceInformation info) {
}
