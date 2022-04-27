package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.ingress.controller.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.application.DeviceCommandDTO;
import pt.sensae.services.device.commander.backend.application.DeviceCommandMapper;
import pt.sensae.services.device.commander.backend.domain.model.commands.CommandId;
import pt.sensae.services.device.commander.backend.domain.model.commands.DeviceCommandRecieved;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.ingress.controller.model.DeviceCommandDTOImpl;

@Service
public class DeviceCommandMapperImpl implements DeviceCommandMapper {

    public DeviceCommandRecieved toModel(DeviceCommandDTO dto) {
        var in = (DeviceCommandDTOImpl) dto;
        return new DeviceCommandRecieved(CommandId.of(in.commandId), DeviceId.of(in.deviceId));
    }
}