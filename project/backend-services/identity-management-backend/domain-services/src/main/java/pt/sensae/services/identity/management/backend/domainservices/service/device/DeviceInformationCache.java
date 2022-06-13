package pt.sensae.services.identity.management.backend.domainservices.service.device;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.device.DeviceInformation;
import pt.sensae.services.identity.management.backend.domain.device.DeviceNotification;
import pt.sensae.services.identity.management.backend.domain.device.NotificationType;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DeviceInformationCache {

    private final Cache<DeviceId, DeviceInformation> cache;

    public DeviceInformationCache() {
        this.cache = Caffeine.newBuilder().build();
    }

    public DeviceInformation findById(DeviceId id) {
        return cache.getIfPresent(id);
    }

    public void refresh(Stream<DeviceNotification> notifications) {
        notifications.forEach(this::notify);
    }

    public void notify(DeviceNotification notification) {
        if (notification.type().equals(NotificationType.DELETE)) {
            this.cache.invalidate(notification.id());
        } else {
            this.cache.put(notification.id(), notification.info());
        }
    }
}
