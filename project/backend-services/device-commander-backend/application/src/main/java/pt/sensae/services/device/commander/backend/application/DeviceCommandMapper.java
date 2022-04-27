package pt.sensae.services.device.commander.backend.application;

import pt.sensae.services.device.commander.backend.domain.model.commands.DeviceCommandRecieved;

public interface DeviceCommandMapper {

    DeviceCommandRecieved toModel(DeviceCommandDTO dto);
}
