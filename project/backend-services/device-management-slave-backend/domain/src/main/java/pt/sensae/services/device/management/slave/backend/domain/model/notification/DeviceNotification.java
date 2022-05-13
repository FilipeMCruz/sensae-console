package pt.sensae.services.device.management.slave.backend.domain.model.notification;

import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceInformation;

public record DeviceNotification(DeviceId id, NotificationType type, DeviceInformation info) {
}
