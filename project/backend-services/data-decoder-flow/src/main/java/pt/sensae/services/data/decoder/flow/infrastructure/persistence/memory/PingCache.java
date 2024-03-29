package pt.sensae.services.data.decoder.flow.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pt.sensae.services.data.decoder.flow.domain.PingRepository;
import pt.sensae.services.data.decoder.flow.domain.SensorTypeId;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.stream.Stream;

@ApplicationScoped
public class PingCache implements PingRepository {

    private final Cache<SensorTypeId, SensorTypeId> cache;

    public PingCache(@ConfigProperty(name = "sensae.cache.data.processor.maxsize") int maxSizeCache) {
        this.cache = Caffeine.newBuilder()
                .maximumSize(maxSizeCache)
                .build();
    }

    @Override
    public void store(SensorTypeId ping) {
        cache.put(ping, ping);
    }

    @Override
    public Stream<SensorTypeId> retrieveAll() {
        var values = new HashSet<>(cache.asMap().values());
        cache.invalidateAll();
        return values.stream();
    }
}
