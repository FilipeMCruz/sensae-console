package sharespot.services.data.decoder.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import sharespot.services.data.decoder.domain.DataDecoder;
import sharespot.services.data.decoder.domain.SensorTypeId;

import java.time.Duration;
import java.util.Optional;

@Service
public class DataDecoderCache {

    private final Cache<SensorTypeId, DataDecoder> cache;

    public DataDecoderCache() {
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(10)
                .build();
    }

    public Optional<DataDecoder> findById(SensorTypeId id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }

    public void update(DataDecoder info) {
        cache.put(info.id(), info);
    }

    public void delete(SensorTypeId id) {
        cache.invalidate(id);
    }
}
