package sharespot.services.devicerecordsbackend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.RecordsRepository;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Service
public class DeviceRecordCache {

    private final Cache<DeviceId, Optional<DeviceRecords>> cache;

    private final RecordsRepository repository;

    public DeviceRecordCache(RecordsRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(50)
                .build();
    }

    public Optional<DeviceRecords> findByDeviceId(DeviceId id) {
        return Objects.requireNonNullElseGet(cache.getIfPresent(id), () -> update(id));
    }

    public Optional<DeviceRecords> update(DeviceId id) {
        var deviceId = repository.findByDeviceId(id);
        cache.put(id, deviceId);
        return deviceId;
    }
}
