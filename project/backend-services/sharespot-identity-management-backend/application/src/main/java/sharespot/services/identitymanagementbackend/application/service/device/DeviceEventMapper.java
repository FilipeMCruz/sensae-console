package sharespot.services.identitymanagementbackend.application.service.device;

import sharespot.services.identitymanagementbackend.application.model.device.DeviceDTO;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;

public interface DeviceEventMapper {
    DeviceDTO domainToDto(DeviceId domain);
}
