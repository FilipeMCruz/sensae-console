package sharespot.services.devicerecordsbackend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.permissions.DevicePermissions;
import sharespot.services.devicerecordsbackend.domain.model.permissions.PermissionsRepository;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;

import java.time.Duration;

@Service
public class DeviceDomainsCache {

    private final Cache<DeviceId, DevicePermissions> cache;

    private final PermissionsRepository repository;

    public DeviceDomainsCache(PermissionsRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(50)
                .build();
    }

    public void verify(DevicePermissions permissions) {
        var perm = cache.getIfPresent(permissions.device());
        if (perm == null || !perm.equals(permissions)) {
            repository.update(permissions);
            cache.put(permissions.device(), permissions);
        }
    }
}
