package pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import pt.sensae.services.data.decoder.master.backend.domain.LastTimeSeenDecoderRepository;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;

import java.time.Instant;
import java.util.Optional;

@Service
public class LastTimeSeenDecoderCache implements LastTimeSeenDecoderRepository {
    private final Cache<SensorTypeId, Instant> cache;

    public LastTimeSeenDecoderCache() {
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
