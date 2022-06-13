package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceNotificationDTO;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceInformationMapper;
import pt.sensae.services.identity.management.backend.domain.device.DeviceInformation;
import pt.sensae.services.identity.management.backend.domain.device.DeviceName;
import pt.sensae.services.identity.management.backend.domain.device.DeviceNotification;
import pt.sensae.services.identity.management.backend.domain.device.NotificationType;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.device.DeviceNotificationDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.device.DeviceNotificationTypeDTOImpl;

import java.util.UUID;

@Service
public class DeviceInformationMapperImpl implements DeviceInformationMapper {

    @Override
    public DeviceNotification dtoToDomain(DeviceNotificationDTO dto) {
        var notification = (DeviceNotificationDTOImpl) dto;
        var deviceId = new DeviceId(UUID.fromString(notification.deviceId));

        if (notification.type.equals(DeviceNotificationTypeDTOImpl.UPDATE)) {
            var deviceName = new DeviceName(notification.information.name);

            var info = new DeviceInformation(deviceId, deviceName);
            return new DeviceNotification(deviceId, NotificationType.UPDATE, info);
        } else {
            return new DeviceNotification(deviceId, NotificationType.DELETE, null);
        }
    }
}
