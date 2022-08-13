package pt.sensae.services.data.processor.flow.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pt.sensae.services.data.processor.flow.domain.DataProcessorRepository;
import pt.sensae.services.data.processor.flow.domain.DataTransformation;
import pt.sensae.services.data.processor.flow.domain.SensorTypeId;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
public class DataProcessorCache implements DataProcessorRepository {

    private final Cache<SensorTypeId, DataTransformation> cache;

    public DataProcessorCache() {
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(10)
                .build();
    }

    @Override
    public Optional<DataTransformation> findById(SensorTypeId id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }

    @Override
    public void update(DataTransformation info) {
        cache.put(info.id(), info);
    }

    @Override
    public void delete(SensorTypeId id) {
        cache.invalidate(id);
    }
}
