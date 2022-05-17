package sharespot.services.identitymanagementbackend.domainservices.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.device.*;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;

import java.util.List;
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

    public DeviceWithAllPermissions collect(DeviceId id) {
        var optionalDevice = deviceRepo.findDeviceById(id);
        if (optionalDevice.isPresent()) {
            var collect = domainRepo.getDomains(optionalDevice.get()
                            .getDomains()
                            .stream()
                            .map(DeviceDomainPermissions::domain))
                    .flatMap(d -> d.getPath().path().stream())
                    .collect(Collectors.toSet());
            return new DeviceWithAllPermissions(id, collect);
        } else {
            var rootDomain = domainRepo.getRootDomain();
            var owner = new DeviceDomainPermissions(rootDomain.getOid(), DevicePermissions.READ_WRITE);
            var newDevice = deviceRepo.add(new Device(id, List.of(owner)));
            return new DeviceWithAllPermissions(newDevice.getOid(), Set.of(rootDomain.getOid()));
        }
    }
}
