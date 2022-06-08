package pt.sensae.services.identity.management.backend.application.internal.identity.device;

import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceWithAllOwnerDomains;

public interface DeviceIdentityMapper {

    DeviceIdentityDTO domainToDto(DeviceWithAllOwnerDomains domain);
}
