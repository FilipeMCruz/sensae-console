package sharespot.services.identitymanagementbackend.domainservices.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceRepository;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceWithAllOwnerDomains;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CollectAllDevices {

    private final DeviceRepository repository;

    private final DomainRepository domainRepo;


    public CollectAllDevices(DeviceRepository deviceRepo, DomainRepository domainRepo) {
        this.repository = deviceRepo;
        this.domainRepo = domainRepo;
    }


    public Stream<DeviceWithAllOwnerDomains> collect() {
        var devices = repository.findAll();
        var domains = domainRepo.findAll().collect(Collectors.toSet());

        return devices.map(device -> {
            var owners = domains.stream().filter(domain -> device.domains().contains(domain.getOid()))
                    .flatMap(d -> d.getPath().path().stream())
                    .collect(Collectors.toSet());
            return new DeviceWithAllOwnerDomains(device.oid(), owners);
        });
    }
}
