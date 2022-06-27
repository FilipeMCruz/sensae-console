package pt.sensae.services.device.management.master.backend.application;

import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.commands.DeviceCommand;
import pt.sensae.services.device.management.master.backend.domain.model.device.Device;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

public interface DeviceEventMapper {

    DeviceCommandDTO domainToCommandDto(DeviceCommand command);

    DeviceNotificationDTO domainToUpdatedDto(DeviceInformation domain);

    DeviceNotificationDTO domainToDeletedDto(DeviceId domain);

    Device dtoToDomain(DeviceDTO dto);
}
