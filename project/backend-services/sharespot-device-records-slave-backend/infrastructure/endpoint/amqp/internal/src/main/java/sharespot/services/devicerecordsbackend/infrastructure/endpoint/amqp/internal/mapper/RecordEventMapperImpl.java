package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.DeviceIdDTO;
import sharespot.services.devicerecordsbackend.application.RecordEventMapper;
import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;

@Service
public class RecordEventMapperImpl implements RecordEventMapper {

    @Override
    public DeviceId dtoToDomain(DeviceIdDTO dto) {
        var eventDTO = (DeviceIdDTOImpl) dto;
        return new DeviceId(eventDTO.id);
    }
}
