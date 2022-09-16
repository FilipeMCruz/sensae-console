package pt.sensae.services.device.management.flow.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pt.sensae.services.device.management.flow.domain.PingRepository;
import pt.sensae.services.device.management.flow.domain.device.Device;
import pt.sensae.services.device.management.flow.domain.device.DeviceId;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.stream.Stream;

@ApplicationScoped
public class PingCache implements PingRepository {

    private final Cache<DeviceId, Device> cache;

    public PingCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(200)
                .build();
    }

    @Override
    public void store(Device ping) {
        cache.put(ping.id(), ping);
    }

    @Override
    public Stream<Device> retrieveAll() {
        var values = new HashSet<>(cache.asMap().values());
        cache.invalidateAll();
        return values.stream();
    }
}