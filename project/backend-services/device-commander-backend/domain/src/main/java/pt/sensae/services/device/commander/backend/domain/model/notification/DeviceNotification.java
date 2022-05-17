package pt.sensae.services.device.commander.backend.domain.model.notification;

import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.commander.backend.domain.model.records.DeviceInformation;

public record DeviceNotification(DeviceId id, NotificationType type, DeviceInformation info) {
}
