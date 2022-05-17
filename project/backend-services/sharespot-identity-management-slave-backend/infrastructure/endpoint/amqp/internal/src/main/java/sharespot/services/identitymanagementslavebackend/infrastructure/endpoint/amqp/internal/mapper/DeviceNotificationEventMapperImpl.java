package sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.application.DeviceIdDTO;
import sharespot.services.identitymanagementslavebackend.application.DeviceNotificationEventMapper;
import sharespot.services.identitymanagementslavebackend.application.OwnershipNotificationDTO;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.DeviceWithAllPermissions;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.notification.NotificationType;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.notification.OwnershipNotification;
import sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;
import sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;
import sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationTypeDTOImpl;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceNotificationEventMapperImpl implements DeviceNotificationEventMapper {

    @Override
    public DeviceIdDTO domainToDto(DeviceId domain) {
        var dto = new DeviceIdDTOImpl();
        dto.id = domain.value().toString();
        return dto;
    }

    @Override
    public OwnershipNotification dtoToDomain(OwnershipNotificationDTO dto) {
        var notification = (DeviceNotificationDTOImpl) dto;
        var deviceId = new DeviceId(UUID.fromString(notification.deviceId));
        if (notification.type.equals(DeviceNotificationTypeDTOImpl.UPDATE)) {
            var domains = notification.information.owners.stream()
                    .map(UUID::fromString)
                    .map(DomainId::new)
                    .collect(Collectors.toSet());
            var info = new DeviceWithAllPermissions(deviceId, domains);
            return new OwnershipNotification(deviceId, NotificationType.UPDATE, info);
        } else {
            return new OwnershipNotification(deviceId, NotificationType.DELETE, null);
        }
    }
}
