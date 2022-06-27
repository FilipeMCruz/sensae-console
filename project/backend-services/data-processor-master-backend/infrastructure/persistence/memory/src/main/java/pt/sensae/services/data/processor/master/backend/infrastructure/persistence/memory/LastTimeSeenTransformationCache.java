package pt.sensae.services.data.processor.master.backend.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.domain.LastTimeSeenTransformationRepository;
import pt.sensae.services.data.processor.master.backend.domain.SensorTypeId;

import java.time.Instant;
import java.util.Optional;

@Service
public class LastTimeSeenTransformationCache implements LastTimeSeenTransformationRepository {
    private final Cache<SensorTypeId, Instant> cache;

    public LastTimeSeenTransformationCache() {
        this.cache = Caffeine.newBuilder().build();
    }

    @Override
    public void update(SensorTypeId id) {
        this.cache.put(id, Instant.now());
    }

    @Override
    public Optional<Instant> find(SensorTypeId id) {
        return Optional.ofNullable(this.cache.getIfPresent(id));
    }
}
