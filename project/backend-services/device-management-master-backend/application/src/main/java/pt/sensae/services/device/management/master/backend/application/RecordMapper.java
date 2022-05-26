package pt.sensae.services.device.management.master.backend.application;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;

public interface RecordMapper {

    DeviceInformation dtoToDomain(DeviceInformationDTO dto);

    DeviceInformationDTO domainToDto(DeviceInformation domain);

    DeviceId dtoToDomain(DeviceDTO dto);

    DeviceDTO domainToDto(DeviceId domain);

}
