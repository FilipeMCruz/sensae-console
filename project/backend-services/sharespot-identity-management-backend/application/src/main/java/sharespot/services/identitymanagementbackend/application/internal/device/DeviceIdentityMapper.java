package sharespot.services.identitymanagementbackend.application.internal.device;

import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceWithAllOwnerDomains;

public interface DeviceIdentityMapper {

    DeviceIdentityDTO domainToDto(DeviceWithAllOwnerDomains domain);
}
