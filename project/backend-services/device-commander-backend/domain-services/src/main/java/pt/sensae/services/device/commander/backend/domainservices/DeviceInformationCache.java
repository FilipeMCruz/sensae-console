package pt.sensae.services.device.commander.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.commander.backend.domain.model.records.DeviceInformation;

import java.time.Duration;
import java.util.Optional;

@Service
public class DeviceInformationCache {

    private final Cache<DeviceId, DeviceInformation> cache;

    public DeviceInformationCache() {
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(100)
                .build();
    }

    public Optional<DeviceInformation> findById(DeviceId id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }

    public void update(DeviceInformation info) {
        if (!info.commands().entries().isEmpty())
            cache.put(info.device().id(), info);
    }

    public void delete(DeviceId id) {
        cache.invalidate(id);
    }
}
