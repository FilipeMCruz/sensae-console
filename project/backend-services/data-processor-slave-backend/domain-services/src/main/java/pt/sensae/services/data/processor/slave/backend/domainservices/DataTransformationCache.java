package pt.sensae.services.data.processor.slave.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.slave.backend.domain.DataTransformation;
import pt.sensae.services.data.processor.slave.backend.domain.SensorTypeId;

import java.time.Duration;
import java.util.Optional;

@Service
public class DataTransformationCache {

    private final Cache<SensorTypeId, DataTransformation> cache;

    public DataTransformationCache() {
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(20)
                .build();
    }

    public Optional<DataTransformation> findById(SensorTypeId id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }

    public void update(DataTransformation info) {
        cache.put(info.getId(), info);
    }

    public void delete(SensorTypeId id) {
        cache.invalidate(id);
    }
}
