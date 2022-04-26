package pt.sensae.services.device.management.master.backend.application;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceRecords;

public interface RecordMapper {

    DeviceRecords dtoToDomain(DeviceRecordDTO dto);

    DeviceRecordDTO domainToDto(DeviceRecords domain);

    DeviceId dtoToDomain(DeviceDTO dto);

    DeviceDTO domainToDto(DeviceId domain);

}
