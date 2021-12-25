package sharespot.services.dataprocessor.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import sharespot.services.dataprocessor.domain.DataTransformation;
import sharespot.services.dataprocessor.domain.SensorDataTransformationsRepository;
import sharespot.services.dataprocessor.domain.SensorTypeId;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Service
public class DataTransformationCache {

    private final Cache<SensorTypeId, Optional<DataTransformation>> cache;
    private final SensorDataTransformationsRepository repository;

    public DataTransformationCache(SensorDataTransformationsRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(20)
                .build();
    }

    public Optional<DataTransformation> findByDeviceId(SensorTypeId id) {
        return Objects.requireNonNullElseGet(cache.getIfPresent(id), () -> update(id));
    }

    public Optional<DataTransformation> update(SensorTypeId id) {
        var deviceType = repository.findByDeviceType(id);
        cache.put(id, deviceType);
        return deviceType;
    }
}
