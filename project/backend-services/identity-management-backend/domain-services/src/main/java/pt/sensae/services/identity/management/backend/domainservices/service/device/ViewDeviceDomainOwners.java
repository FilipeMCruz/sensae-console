package pt.sensae.services.identity.management.backend.domainservices.service.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.identity.device.Device;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceRepository;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceWithAllOwnerDomains;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;

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
        if (id.belongsToRoot()) {
            return new DeviceWithAllOwnerDomains(id, Set.of(domainRepo.getRootDomain().getOid()));
        }
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
