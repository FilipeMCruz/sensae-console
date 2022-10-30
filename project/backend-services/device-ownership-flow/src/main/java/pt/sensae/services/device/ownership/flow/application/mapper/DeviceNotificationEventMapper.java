package pt.sensae.services.device.ownership.flow.application.mapper;

import pt.sensae.services.device.ownership.flow.application.model.DeviceIdDTO;
import pt.sensae.services.device.ownership.flow.application.model.OwnershipNotificationDTO;
import pt.sensae.services.device.ownership.flow.domain.DeviceId;
import pt.sensae.services.device.ownership.flow.domain.OwnershipNotification;

public interface DeviceNotificationEventMapper {

    DeviceIdDTO domainToDto(DeviceId domain);

    OwnershipNotification dtoToDomain(OwnershipNotificationDTO dto);

}
