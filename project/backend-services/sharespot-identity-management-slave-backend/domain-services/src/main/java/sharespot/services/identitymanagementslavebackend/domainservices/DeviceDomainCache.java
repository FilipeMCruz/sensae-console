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
        var device = new Device(id, List.of(new DeviceDomainPermissions(rootDomain.getOid(), DevicePermissions.READ_WRITE)));
        var add = repository.add(device);
        return new DeviceWithAllPermissions(add.getOid(), new ArrayList<>(), List.of(rootDomain.getOid()));
    }

    private DeviceWithAllPermissions build(Device device) {
        //fetch all domains from repo
        var domains = domainRepository.findDomainsById(device.getDomains().stream().map(DeviceDomainPermissions::domain).toList());

        //get all device domain' ids with write permission
        var writeDomains = device.getDomains()
                .stream()
                .filter(d -> d.permissions().equals(DevicePermissions.READ_WRITE))
                .map(DeviceDomainPermissions::domain)
                .toList();

        //get all device write domain's parents
        var writeDomainIds = domains
                .stream()
                .filter(d -> writeDomains.contains(d.getOid()))
                .flatMap(d -> d.getPath().path().stream())
                .distinct()
                .toList();

        //get all device domains with read permission
        var readDomains = device.getDomains()
                .stream()
                .filter(d -> d.permissions().equals(DevicePermissions.READ))
                .map(DeviceDomainPermissions::domain)
                .toList();

        //get all device read domain's parents excluding the ones with write permissions
        var readDomainIds = domains
                .stream()
                .filter(d -> readDomains.contains(d.getOid()))
                .flatMap(d -> d.getPath().path().stream())
                .filter(d -> !writeDomainIds.contains(d))
                .distinct()
                .toList();

        return new DeviceWithAllPermissions(device.getOid(), readDomainIds, writeDomainIds);
    }
}
