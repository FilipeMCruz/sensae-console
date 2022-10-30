package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.ingress.mapper;

import pt.sensae.services.device.commander.application.mapper.DeviceCommandMapper;
import pt.sensae.services.device.commander.application.model.DeviceCommandDTO;
import pt.sensae.services.device.commander.domain.commands.CommandId;
import pt.sensae.services.device.commander.domain.commands.DeviceCommandReceived;
import pt.sensae.services.device.commander.domain.device.DeviceId;
import pt.sensae.services.device.commander.infrastructure.endpoint.amqp.ingress.model.DeviceCommandDTOImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeviceCommandMapperImpl implements DeviceCommandMapper {

    public DeviceCommandReceived toModel(DeviceCommandDTO dto) {
        var in = (DeviceCommandDTOImpl) dto;
        return new DeviceCommandReceived(CommandId.of(in.commandId), DeviceId.of(in.deviceId));
    }
}
