package pt.sensae.services.device.ownership.flow.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pt.sensae.services.device.ownership.flow.domain.DeviceId;
import pt.sensae.services.device.ownership.flow.domain.PingRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.stream.Stream;

@ApplicationScoped
public class PingCache implements PingRepository {

    private final Cache<DeviceId, DeviceId> cache;

    public PingCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(20)
                .build();
    }

    @Override
    public void store(DeviceId ping) {
        cache.put(ping, ping);
    }

    @Override
    public Stream<DeviceId> retrieveAll() {
        var values = new HashSet<>(cache.asMap().values());
        cache.invalidateAll();
        return values.stream();
    }
}