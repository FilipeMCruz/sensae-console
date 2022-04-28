package pt.sensae.services.device.management.slave.backend.domain.model.commands;

import pt.sensae.services.device.management.slave.backend.domain.model.subDevices.DeviceRef;

public record CommandEntry(CommandId id, CommandName name, CommandPayload payload, CommandPort port, DeviceRef ref) {
}
