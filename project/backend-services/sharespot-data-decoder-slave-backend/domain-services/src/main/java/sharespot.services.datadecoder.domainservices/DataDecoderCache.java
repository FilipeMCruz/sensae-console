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

    private final Cache<SensorTypeId, Optional<DataDecoder>> cache;

    private final SensorDataDecodersRepository repository;

    public DataDecoderCache(SensorDataDecodersRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(10)
                .build();
    }

    public Optional<DataDecoder> update(SensorTypeId id) {
        var deviceType = repository.findByDeviceType(id);
        cache.invalidate(id);
        cache.put(id, deviceType);
        return deviceType;
    }

    public Optional<DataDecoder> findByDeviceId(SensorTypeId id) {
        return Objects.requireNonNullElseGet(cache.getIfPresent(id), () -> update(id));
    }
}
