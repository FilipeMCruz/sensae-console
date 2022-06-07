package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceIdentityMapper;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceIdentityDTO;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceWithAllOwnerDomains;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.DeviceWithOwnershipDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.DeviceIdentityDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.DeviceNotificationTypeDTOImpl;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceIdentityMapperImpl implements DeviceIdentityMapper {

    @Override
    public DeviceIdentityDTO domainToDto(DeviceWithAllOwnerDomains domain) {
        var info = new DeviceWithOwnershipDTOImpl();
        info.deviceId = domain.oid().value().toString();
        info.owners = domain.ownerDomains()
                .stream()
                .map(DomainId::value)
                .map(UUID::toString)
                .collect(Collectors.toList());

        var notification = new DeviceIdentityDTOImpl();
        notification.deviceId = info.deviceId;
        notification.type = DeviceNotificationTypeDTOImpl.UPDATE;
        notification.information = info;
        return notification;
    }
}
