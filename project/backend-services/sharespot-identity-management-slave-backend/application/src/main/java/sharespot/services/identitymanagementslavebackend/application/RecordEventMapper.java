package sharespot.services.identitymanagementslavebackend.application;

import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;

public interface RecordEventMapper {

    DeviceId dtoToDomain(DeviceIdDTO dto);

}
