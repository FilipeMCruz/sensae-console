package pt.sensae.services.device.management.flow.domain.commands;

import pt.sensae.services.device.management.flow.domain.subDevices.DeviceRef;

public record CommandEntry(CommandId id, CommandName name, CommandPayload payload, CommandPort port, DeviceRef ref) {
}
