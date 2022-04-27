package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.egress.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.model.command.DeviceCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.mapper.command.DeviceCommandMapper;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.command.DeviceCommand;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.egress.model.DeviceCommandDTOImpl;

@Service
public class DeviceCommandMapperImpl implements DeviceCommandMapper {

    public DeviceCommandDTO toDTO(DeviceCommand model) {
        var in = new DeviceCommandDTOImpl();
        in.commandId = model.commandId().commandId();
        in.deviceId = model.deviceId().value();
        return in;
    }
}
