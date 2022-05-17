package sharespot.services.identitymanagementbackend.domainservices.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.device.*;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ViewDeviceDomainOwners {

    private final DomainRepository domainRepo;

    private final DeviceRepository deviceRepo;

    public ViewDeviceDomainOwners(DomainRepository domainRepo, DeviceRepository deviceRepo) {
        this.domainRepo = domainRepo;
        this.deviceRepo = deviceRepo;
    }

    public DeviceWithAllOwnerDomains collect(DeviceId id) {
        var optionalDevice = deviceRepo.findDeviceById(id);
        if (optionalDevice.isPresent()) {
            var collect = domainRepo.getDomains(optionalDevice.get().domains().stream())
                    .flatMap(d -> d.getPath().path().stream())
                    .collect(Collectors.toSet());
            return new DeviceWithAllOwnerDomains(id, collect);
        } else {
            var rootDomain = Set.of(domainRepo.getRootDomain().getOid());
            var newDevice = deviceRepo.add(new Device(id, rootDomain));
            return new DeviceWithAllOwnerDomains(newDevice.oid(), newDevice.domains());
        }
    }
}
