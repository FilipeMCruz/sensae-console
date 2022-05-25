package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.notification.NotificationType;
import sharespot.services.identitymanagementslavebackend.domainservices.DeviceDomainCache;
import sharespot.services.identitymanagementslavebackend.domainservices.UnhandledAlertCache;
import sharespot.services.identitymanagementslavebackend.domainservices.UnhandledSensorDataCache;

@Service
public class DeviceOwnershipNotifierService {

    private final DeviceDomainCache cache;

    private final UnhandledSensorDataCache unhandledSensorDataCache;

    private final DeviceNotificationEventMapper mapper;

    private final SensorDataPublisherService handler;
    private final AlertPublisherService alertHandler;

    private final UnhandledAlertCache unhandledAlertCache;

    public DeviceOwnershipNotifierService(DeviceDomainCache cache,
                                          DeviceNotificationEventMapper mapper,
                                          SensorDataPublisherService handler,
                                          AlertPublisherService alertHandler,
                                          UnhandledSensorDataCache unhandledSensorDataCache,
                                          UnhandledAlertCache unhandledAlertCache) {
        this.cache = cache;
        this.mapper = mapper;
        this.handler = handler;
        this.alertHandler = alertHandler;
        this.unhandledAlertCache = unhandledAlertCache;
        this.unhandledSensorDataCache = unhandledSensorDataCache;
    }

    public void info(OwnershipNotificationDTO dto) {
        var notification = mapper.dtoToDomain(dto);
        if (notification.type().equals(NotificationType.DELETE)) {
            cache.delete(notification.id());
        } else {
            cache.update(notification.info());
            unhandledSensorDataCache.retrieve(notification.id()).forEach(handler::publish);
            unhandledAlertCache.retrieve(notification.id()).forEach(alertHandler::publish);
        }
    }
}
