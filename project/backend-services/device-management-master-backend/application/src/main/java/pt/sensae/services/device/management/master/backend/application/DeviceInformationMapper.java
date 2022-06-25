package pt.sensae.services.device.management.master.backend.application;

import pt.sensae.services.device.management.master.backend.application.command.DeviceCommandDTO;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.commands.DeviceCommand;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

public interface DeviceInformationMapper {

    DeviceInformation dtoToDomain(DeviceInformationDTO dto);

    DeviceInformationDTO domainToDto(DeviceInformation domain);

    DeviceId dtoToDomain(DeviceDTO dto);

    DeviceDTO domainToDto(DeviceId domain);

    DeviceCommand dtoToDomain(DeviceCommandDTO command);
}
