package pt.sensae.services.smart.irrigation.backend.application.mapper.command;

import pt.sensae.services.smart.irrigation.backend.application.model.command.DeviceCommandDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.command.DeviceCommand;

public interface DeviceCommandMapper {

    DeviceCommandDTO toDTO(DeviceCommand model);
}
