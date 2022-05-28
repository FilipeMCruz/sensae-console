package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.internal.device.DeviceNotificationDTO;
import sharespot.services.identitymanagementbackend.application.internal.device.NotificationEventMapper;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceWithAllOwnerDomains;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model.DeviceNotificationDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model.DeviceNotificationTypeDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model.DeviceWithOwnershipDTOImpl;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationEventMapperImpl implements NotificationEventMapper {

    @Override
    public DeviceNotificationDTO domainToUpdatedDto(DeviceWithAllOwnerDomains domain) {
        var info = new DeviceWithOwnershipDTOImpl();
        info.deviceId = domain.oid().value().toString();
        info.owners = domain.ownerDomains()
                .stream()
                .map(DomainId::value)
                .map(UUID::toString)
                .collect(Collectors.toList());

        var notification = new DeviceNotificationDTOImpl();
        notification.deviceId = info.deviceId;
        notification.type = DeviceNotificationTypeDTOImpl.UPDATE;
        notification.information = info;
        return notification;
    }
}
