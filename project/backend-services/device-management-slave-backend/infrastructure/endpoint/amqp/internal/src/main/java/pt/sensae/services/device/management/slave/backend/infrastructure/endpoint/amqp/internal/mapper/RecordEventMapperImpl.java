package pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.application.DeviceIdDTO;
import pt.sensae.services.device.management.slave.backend.application.RecordEventMapper;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;

@Service
public class RecordEventMapperImpl implements RecordEventMapper {

    @Override
    public DeviceId dtoToDomain(DeviceIdDTO dto) {
        var eventDTO = (DeviceIdDTOImpl) dto;
        return new DeviceId(eventDTO.id);
    }
}
