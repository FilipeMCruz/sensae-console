package sharespot.services.identitymanagementbackend.domainservices.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceRepository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domainservices.mapper.DeviceResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceResult;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;

import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ViewTenantDevices {

    private final DeviceRepository deviceRepo;

    public ViewTenantDevices(DeviceRepository deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public Stream<DeviceResult> fetch(IdentityCommand identity) {
        var domainIdStream = identity.domains.stream().map(UUID::fromString).map(DomainId::new);
        return deviceRepo.getDevicesInDomains(domainIdStream)
                .map(DeviceResultMapper::toResult);
    }
}
