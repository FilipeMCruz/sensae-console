package pt.sensae.services.device.ownership.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.ownership.backend.domain.device.DeviceId;
import pt.sensae.services.device.ownership.backend.domain.DeviceWithAllPermissions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeviceDomainCache {

    private final Cache<DeviceId, DeviceWithAllPermissions> cache;

    public DeviceDomainCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(100)
                .build();
    }

    public Optional<DeviceWithAllPermissions> findById(DeviceId id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }

    public void update(DeviceWithAllPermissions info) {
        cache.put(info.getOid(), info);
    }

    public void delete(DeviceId id) {
        cache.invalidate(id);
    }

    public Optional<List<DeviceWithAllPermissions>> findAllById(Stream<DeviceId> devices) {
        var possibleDevices = devices.map(this::findById).toList();
        if (possibleDevices.isEmpty()) {
            return this.findById(DeviceId.root()).map(List::of);
        }

        if (possibleDevices.stream().anyMatch(Optional::isEmpty)) {
            return Optional.empty();
        } else {
            return Optional.of(possibleDevices.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()));
        }
    }
}
