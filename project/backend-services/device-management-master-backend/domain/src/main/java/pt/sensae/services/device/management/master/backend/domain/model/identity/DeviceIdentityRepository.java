package pt.sensae.services.device.management.master.backend.domain.model.identity;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

import java.util.stream.Stream;

public interface DeviceIdentityRepository {
    
    Stream<DeviceId> owns(Stream<DomainId> domainIds);

    void update(DeviceWithAllOwnerDomains device);

    void refresh(Stream<DeviceWithAllOwnerDomains> devices);
}
