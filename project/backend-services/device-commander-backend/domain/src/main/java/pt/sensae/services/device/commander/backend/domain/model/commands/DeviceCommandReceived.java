package pt.sensae.services.device.commander.backend.domain.model.commands;

import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;

public record DeviceCommandReceived(CommandId commandId, DeviceId deviceId) {
}
