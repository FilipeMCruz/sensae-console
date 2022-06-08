package pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.ownership.backend.application.DeviceIdDTO;
import pt.sensae.services.device.ownership.backend.application.DeviceNotificationEventMapper;
import pt.sensae.services.device.ownership.backend.application.OwnershipNotificationDTO;
import pt.sensae.services.device.ownership.backend.domain.DeviceWithAllPermissions;
import pt.sensae.services.device.ownership.backend.domain.device.DeviceId;
import pt.sensae.services.device.ownership.backend.domain.domain.DomainId;
import pt.sensae.services.device.ownership.backend.domain.notification.NotificationType;
import pt.sensae.services.device.ownership.backend.domain.notification.OwnershipNotification;
import pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;
import pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;
import pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationTypeDTOImpl;

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
