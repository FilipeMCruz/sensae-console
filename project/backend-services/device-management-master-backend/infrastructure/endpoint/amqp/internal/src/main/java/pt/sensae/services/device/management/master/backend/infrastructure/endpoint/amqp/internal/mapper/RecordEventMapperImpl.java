package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.mapper;

import pt.sensae.services.device.management.master.backend.application.DeviceDTO;
import pt.sensae.services.device.management.master.backend.application.RecordEventMapper;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

@Service
public class RecordEventMapperImpl implements RecordEventMapper {

    @Override
    public DeviceDTO domainToDto(DeviceId domain) {
        var deviceDTO = new DeviceIdDTOImpl();
        deviceDTO.id = domain.value();
        return deviceDTO;
    }
}
