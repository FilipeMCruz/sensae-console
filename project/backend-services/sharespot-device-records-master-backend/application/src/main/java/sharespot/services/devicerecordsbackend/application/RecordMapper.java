package sharespot.services.devicerecordsbackend.application;

import sharespot.services.devicerecordsbackend.domain.model.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;

public interface RecordMapper {

    DeviceRecords dtoToDomain(DeviceRecordDTO dto);

    DeviceRecordDTO domainToDto(DeviceRecords domain);

    DeviceId dtoToDomain(DeviceDTO dto);

    DeviceDTO domainToDto(DeviceId domain);

}
