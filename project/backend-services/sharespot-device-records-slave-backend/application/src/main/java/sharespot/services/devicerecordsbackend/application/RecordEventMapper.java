package sharespot.services.devicerecordsbackend.application;

import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;

public interface RecordEventMapper {

    DeviceRecords dtoToDomain(DeviceRecordIndexedEventDTO dto);
    DeviceId dtoToDomain(DeviceRecordErasedEventDTO dto);
    
}
