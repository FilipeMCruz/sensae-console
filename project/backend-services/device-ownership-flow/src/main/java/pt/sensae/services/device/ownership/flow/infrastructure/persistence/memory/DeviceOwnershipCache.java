package pt.sensae.services.device.ownership.flow.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pt.sensae.services.device.ownership.flow.domain.DeviceId;
import pt.sensae.services.device.ownership.flow.domain.DeviceOwnershipRepository;
import pt.sensae.services.device.ownership.flow.domain.DeviceWithAllPermissions;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class DeviceOwnershipCache implements DeviceOwnershipRepository {

    private final Cache<DeviceId, DeviceWithAllPermissions> cache;

    public DeviceOwnershipCache(@ConfigProperty(name = "sensae.cache.devices.ownership.maxsize") int maxSizeCache) {
        this.cache = Caffeine.newBuilder()
                .maximumSize(maxSizeCache)
                .build();
    }

    @Override
    public Optional<DeviceWithAllPermissions> findById(DeviceId id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }

    @Override
    public void update(DeviceWithAllPermissions info) {
        cache.put(info.oid(), info);
    }


    @Override
    public void delete(DeviceId id) {
        cache.invalidate(id);
    }


    @Override
    public Optional<List<DeviceWithAllPermissions>> findAllById(Stream<DeviceId> devices) {
        var possibleDevices = devices.map(this::findById).toList();
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
