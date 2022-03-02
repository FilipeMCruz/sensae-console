package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceDTO;
import sharespot.services.identitymanagementbackend.application.service.device.DeviceEventMapper;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model.DeviceIdDTOImpl;

@Service
public class DeviceEventMapperImpl implements DeviceEventMapper {

    @Override
    public DeviceDTO domainToDto(DeviceId domain) {
        var deviceDTO = new DeviceIdDTOImpl();
        deviceDTO.id = domain.value();
        return deviceDTO;
    }
}
