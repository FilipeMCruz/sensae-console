package pt.sensae.services.device.commander.domain.notification;

import pt.sensae.services.device.commander.domain.DeviceInformation;
import pt.sensae.services.device.commander.domain.device.DeviceId;

public record DeviceNotification(DeviceId id, NotificationType type, DeviceInformation info) {
}
