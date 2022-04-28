package pt.sensae.services.device.management.master.backend.application;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceInformation;

public interface RecordMapper {

    DeviceInformation dtoToDomain(DeviceRecordDTO dto);

    DeviceRecordDTO domainToDto(DeviceInformation domain);

    DeviceId dtoToDomain(DeviceDTO dto);

    DeviceDTO domainToDto(DeviceId domain);

}
