package pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.mapper;

import pt.sensae.services.device.ownership.flow.application.mapper.DeviceNotificationEventMapper;
import pt.sensae.services.device.ownership.flow.application.model.DeviceIdDTO;
import pt.sensae.services.device.ownership.flow.application.model.OwnershipNotificationDTO;
import pt.sensae.services.device.ownership.flow.domain.*;
import pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;
import pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;
import pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.model.DeviceNotificationTypeDTOImpl;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
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
