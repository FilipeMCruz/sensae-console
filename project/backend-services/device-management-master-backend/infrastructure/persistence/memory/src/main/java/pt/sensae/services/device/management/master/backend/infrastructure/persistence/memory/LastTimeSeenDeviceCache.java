package pt.sensae.services.device.management.master.backend.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.LastTimeSeenDeviceRepository;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

import java.time.Instant;
import java.util.Optional;

@Service
public class LastTimeSeenDeviceCache implements LastTimeSeenDeviceRepository {
    private final Cache<DeviceId, Instant> cache;

    public LastTimeSeenDeviceCache() {
        this.cache = Caffeine.newBuilder().build();
    }

    @Override
    public void update(DeviceId id) {
        this.cache.put(id, Instant.now());
    }

    @Override
    public Optional<Instant> find(DeviceId id) {
        return Optional.ofNullable(this.cache.getIfPresent(id));
    }
}
