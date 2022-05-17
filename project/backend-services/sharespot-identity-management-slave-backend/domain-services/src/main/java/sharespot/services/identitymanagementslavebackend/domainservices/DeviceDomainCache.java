package sharespot.services.identitymanagementslavebackend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.DeviceWithAllPermissions;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.*;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DeviceDomainCache {

    private final Cache<DeviceId, DeviceWithAllPermissions> cache;

    private final DeviceRepository repository;
    private final DomainRepository domainRepository;

    public DeviceDomainCache(DeviceRepository repository, DomainRepository domainRepository) {
        this.repository = repository;
        this.domainRepository = domainRepository;
        this.cache = Caffeine.newBuilder()
                .maximumSize(100)
                .build();
    }

    public DeviceWithAllPermissions findByDeviceId(DeviceId id) {
        return Objects.requireNonNullElseGet(cache.getIfPresent(id), () -> update(id));
    }

    public DeviceWithAllPermissions update(DeviceId id) {
        var deviceById = repository.findDeviceById(id);
        var device = deviceById.isEmpty() ? this.create(id) : this.build(deviceById.get());
        cache.put(id, device);
        return device;
    }

    private DeviceWithAllPermissions create(DeviceId id) {
        var rootDomain = domainRepository.getRootDomain();
        var device = new Device(id, List.of(rootDomain.getOid()));
        var add = repository.add(device);
        return new DeviceWithAllPermissions(add.getOid(), List.of(rootDomain.getOid()));
    }

    private DeviceWithAllPermissions build(Device device) {
        //fetch all domains from repo
        var domains = domainRepository.findDomainsById(device.getDomains().stream()).toList();

        //get all device write domain's parents
        var ownerDomainIds = domains
                .stream()
                .flatMap(d -> d.getPath().path().stream())
                .distinct()
                .toList();

        return new DeviceWithAllPermissions(device.getOid(), ownerDomainIds);
    }
}
