package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.command;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ValveCommand;

public record DeviceCommand(ValveCommand commandId, DeviceId deviceId) {
}
