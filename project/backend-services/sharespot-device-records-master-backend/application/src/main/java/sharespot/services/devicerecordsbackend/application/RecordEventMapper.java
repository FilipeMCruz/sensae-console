package sharespot.services.devicerecordsbackend.application;

import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;

public interface RecordEventMapper {

    DeviceRecordEventDTO domainToDto(DeviceRecords domain);

    DeviceRecordEventDTO domainToDto(DeviceId domain);

}
