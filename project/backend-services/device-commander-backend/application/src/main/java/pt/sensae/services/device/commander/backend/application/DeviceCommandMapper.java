package pt.sensae.services.device.commander.backend.application;

import pt.sensae.services.device.commander.backend.domain.model.commands.DeviceCommandReceived;

public interface DeviceCommandMapper {

    DeviceCommandReceived toModel(DeviceCommandDTO dto);
}
