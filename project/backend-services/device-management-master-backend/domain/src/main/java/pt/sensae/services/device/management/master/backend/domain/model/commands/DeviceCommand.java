package pt.sensae.services.device.management.master.backend.domain.model.commands;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

public record DeviceCommand(DeviceId id, CommandId commandId) {
}
