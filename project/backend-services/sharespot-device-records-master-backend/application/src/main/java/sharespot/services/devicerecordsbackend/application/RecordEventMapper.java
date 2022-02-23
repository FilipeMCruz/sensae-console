package sharespot.services.devicerecordsbackend.application;

import sharespot.services.devicerecordsbackend.domain.model.DeviceId;

public interface RecordEventMapper {
    DeviceDTO domainToDto(DeviceId domain);

}
