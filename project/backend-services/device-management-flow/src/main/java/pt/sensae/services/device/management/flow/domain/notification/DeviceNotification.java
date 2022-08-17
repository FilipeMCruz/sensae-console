package pt.sensae.services.device.management.flow.domain.notification;

import pt.sensae.services.device.management.flow.domain.DeviceInformation;
import pt.sensae.services.device.management.flow.domain.device.DeviceId;

public record DeviceNotification(DeviceId id, NotificationType type, DeviceInformation info) {
}
