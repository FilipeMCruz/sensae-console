package pt.sensae.services.data.decoder.flow.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pt.sensae.services.data.decoder.flow.domain.DataDecoder;
import pt.sensae.services.data.decoder.flow.domain.DataDecoderRepository;
import pt.sensae.services.data.decoder.flow.domain.SensorTypeId;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
public class DataDecoderCache implements DataDecoderRepository {

    private final Cache<SensorTypeId, DataDecoder> cache;

    public DataDecoderCache() {
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(10)
                .build();
    }

    @Override
    public Optional<DataDecoder> findById(SensorTypeId id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }

    @Override
    public void update(DataDecoder info) {
        cache.put(info.id(), info);
    }

    @Override
    public void delete(SensorTypeId id) {
        cache.invalidate(id);
    }
}
