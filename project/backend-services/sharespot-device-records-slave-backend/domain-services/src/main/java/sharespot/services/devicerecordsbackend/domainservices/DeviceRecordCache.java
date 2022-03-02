package sharespot.services.devicerecordsbackend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.Device;
import sharespot.services.devicerecordsbackend.domain.model.records.*;

import java.time.Duration;
import java.util.Objects;

@Service
public class DeviceRecordCache {

    private final Cache<DeviceId, DeviceRecords> cache;

    private final RecordsRepository repository;

    public DeviceRecordCache(RecordsRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(50)
                .build();
    }

    public DeviceRecords findByDeviceId(DeviceId id, DeviceName name) {
        return Objects.requireNonNullElseGet(cache.getIfPresent(id), () -> update(id, name));
    }

    public DeviceRecords update(DeviceId id, DeviceName name) {
        var deviceById = repository.findByDeviceId(id);
        var device = deviceById.isEmpty() ? this.create(id, name) : deviceById.get();
        cache.put(id, device);
        return device;
    }

    private DeviceRecords create(DeviceId id, DeviceName name) {
        return repository.add(new DeviceRecords(new Device(id, name), Records.empty()));
    }
}
