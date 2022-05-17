package sharespot.services.identitymanagementslavebackend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.DeviceWithAllPermissions;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.*;

import java.util.Optional;

@Service
public class DeviceDomainCache {

    private final Cache<DeviceId, DeviceWithAllPermissions> cache;

    public DeviceDomainCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(100)
                .build();
    }

    public Optional<DeviceWithAllPermissions> findById(DeviceId id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }

    public void update(DeviceWithAllPermissions info) {
        cache.put(info.getOid(), info);
    }

    public void delete(DeviceId id) {
        cache.invalidate(id);
    }
}
