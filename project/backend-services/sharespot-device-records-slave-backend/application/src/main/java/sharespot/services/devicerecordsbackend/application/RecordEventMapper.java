package sharespot.services.devicerecordsbackend.application;

import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;

public interface RecordEventMapper {

    DeviceId dtoToDomain(DeviceIdDTO dto);
    
}
