package sharespot.services.datadecoder.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import sharespot.services.datadecoder.domain.DataDecoder;
import sharespot.services.datadecoder.domain.SensorDataDecodersRepository;
import sharespot.services.datadecoder.domain.SensorTypeId;

import java.time.Duration;
import java.util.Objects;
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
        cache.put(info.getId(), info);
    }

    public void delete(SensorTypeId id) {
        cache.invalidate(id);
    }
}
