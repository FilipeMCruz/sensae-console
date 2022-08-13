package pt.sensae.services.device.commander.domain.commands;

import pt.sensae.services.device.commander.domain.device.DeviceId;

public record DeviceCommandReceived(CommandId commandId, DeviceId deviceId) {
}
