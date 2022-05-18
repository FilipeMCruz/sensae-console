package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.notification.NotificationType;
import sharespot.services.identitymanagementslavebackend.domainservices.DeviceDomainCache;
import sharespot.services.identitymanagementslavebackend.domainservices.UnhandledSensorDataCache;

@Service
public class DeviceOwnershipNotifierService {

    private final DeviceDomainCache cache;

    private final UnhandledSensorDataCache unhandledSensorDataCache;

    private final DeviceNotificationEventMapper mapper;

    private final SensorDataPublisherService handler;

    public DeviceOwnershipNotifierService(DeviceDomainCache cache,
                                          UnhandledSensorDataCache unhandledSensorDataCache,
                                          DeviceNotificationEventMapper mapper,
                                          SensorDataPublisherService handler) {
        this.cache = cache;
        this.unhandledSensorDataCache = unhandledSensorDataCache;
        this.mapper = mapper;
        this.handler = handler;
    }

    public void info(OwnershipNotificationDTO dto) {
        var notification = mapper.dtoToDomain(dto);
        if (notification.type().equals(NotificationType.DELETE)) {
            cache.delete(notification.id());
        } else {
            cache.update(notification.info());
            unhandledSensorDataCache.retrieve(notification.id()).forEach(handler::publish);
        }
    }
}
