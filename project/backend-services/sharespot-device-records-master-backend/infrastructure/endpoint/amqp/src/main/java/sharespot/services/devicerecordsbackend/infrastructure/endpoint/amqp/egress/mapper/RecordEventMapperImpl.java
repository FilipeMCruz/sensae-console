package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.DeviceDTO;
import sharespot.services.devicerecordsbackend.application.RecordEventMapper;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model.DeviceIdDTOImpl;

@Service
public class RecordEventMapperImpl implements RecordEventMapper {

    @Override
    public DeviceDTO domainToDto(DeviceId domain) {
        var deviceDTO = new DeviceIdDTOImpl();
        deviceDTO.id = domain.value();
        return deviceDTO;
    }
}
