package pt.sensae.services.device.management.master.backend.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DeviceIdentityRepository;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DeviceWithAllOwnerDomains;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DomainId;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class DeviceIdentityCache implements DeviceIdentityRepository {
    private final Cache<DomainId, Set<DeviceId>> cache;

    public DeviceIdentityCache() {
        this.cache = Caffeine.newBuilder().build();
    }

    @Override
    public Stream<DeviceId> owns(Stream<DomainId> domainIds) {
        return this.cache.getAllPresent(domainIds.toList())
                .values()
                .stream()
                .flatMap(Collection::stream);
    }

    @Override
    public void update(DeviceWithAllOwnerDomains device) {
        this.cache.asMap().values().forEach(deviceIds -> deviceIds.remove(device.oid()));

        device.ownerDomains().forEach(domain -> {
            var ifPresent = this.cache.getIfPresent(domain);
            if (ifPresent == null) {
                var deviceId = new HashSet<DeviceId>();
                deviceId.add(device.oid());
                this.cache.put(domain, deviceId);
            } else {
                ifPresent.add(device.oid());
            }
        });
    }

    @Override
    public void refresh(Stream<DeviceWithAllOwnerDomains> devices) {
        var map = new HashMap<DomainId, Set<DeviceId>>();
        devices.forEach(device -> device.ownerDomains()
                .forEach(domain -> map.computeIfAbsent(domain, k -> new HashSet<>()).add(device.oid())));

        this.cache.invalidateAll();
        this.cache.putAll(map);
    }
}
