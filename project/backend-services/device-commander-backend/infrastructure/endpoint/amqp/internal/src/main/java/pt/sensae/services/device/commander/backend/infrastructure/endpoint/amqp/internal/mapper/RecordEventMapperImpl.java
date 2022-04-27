package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;
import pt.sensae.services.device.commander.backend.application.DeviceIdDTO;
import pt.sensae.services.device.commander.backend.application.RecordEventMapper;

@Service
public class RecordEventMapperImpl implements RecordEventMapper {

    @Override
    public DeviceId dtoToDomain(DeviceIdDTO dto) {
        var eventDTO = (DeviceIdDTOImpl) dto;
        return new DeviceId(eventDTO.id);
    }
}
