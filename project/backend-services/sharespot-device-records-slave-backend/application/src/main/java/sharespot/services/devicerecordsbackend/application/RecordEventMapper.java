package sharespot.services.devicerecordsbackend.application;

import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;

public interface RecordEventMapper {

    DeviceId dtoToDomain(DeviceIdDTO dto);
    
}
