package sharespot.services.identitymanagementbackend.domain.identity.device;

import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository {

    Optional<Device> findDeviceById(DeviceId id);

    Device relocateDevice(Device tenant);

    List<Device> getDeviceInDomain(DomainId domain);

    List<Device> getDeviceInDomainAndSubDomains(DomainId domain);
}
