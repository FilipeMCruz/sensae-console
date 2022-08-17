package pt.sensae.services.device.commander.application.mapper;

import pt.sensae.services.device.commander.application.model.DeviceCommandDTO;
import pt.sensae.services.device.commander.domain.commands.DeviceCommandReceived;

public interface DeviceCommandMapper {

    DeviceCommandReceived toModel(DeviceCommandDTO dto);
}
