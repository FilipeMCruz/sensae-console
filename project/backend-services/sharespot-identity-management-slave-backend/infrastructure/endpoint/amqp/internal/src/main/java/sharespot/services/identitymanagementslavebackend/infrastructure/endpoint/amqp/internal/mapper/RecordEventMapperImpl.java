package sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.application.DeviceIdDTO;
import sharespot.services.identitymanagementslavebackend.application.RecordEventMapper;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;

@Service
public class RecordEventMapperImpl implements RecordEventMapper {

    @Override
    public DeviceId dtoToDomain(DeviceIdDTO dto) {
        var eventDTO = (DeviceIdDTOImpl) dto;
        return new DeviceId(eventDTO.id);
    }
}
