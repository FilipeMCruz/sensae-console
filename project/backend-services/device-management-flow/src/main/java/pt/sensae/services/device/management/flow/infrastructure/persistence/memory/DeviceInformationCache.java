package pt.sensae.services.device.management.flow.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pt.sensae.services.device.management.flow.domain.DeviceInformation;
import pt.sensae.services.device.management.flow.domain.DeviceInformationRepository;
import pt.sensae.services.device.management.flow.domain.device.DeviceId;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
public class DeviceInformationCache implements DeviceInformationRepository {

    private final Cache<DeviceId, DeviceInformation> cache;

    public DeviceInformationCache(@ConfigProperty(name = "sensae.cache.devices.information.maxsize") int maxSizeCache) {
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(maxSizeCache)
                .build();
    }

    @Override
    public Optional<DeviceInformation> findById(DeviceId id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }

    @Override
    public void update(DeviceInformation info) {
        cache.put(info.device().id(), info);
    }

    @Override
    public void delete(DeviceId id) {
        cache.invalidate(id);
    }
}
