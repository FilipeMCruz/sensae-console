package pt.sensae.services.device.management.slave.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.domain.model.notification.NotificationType;
import pt.sensae.services.device.management.slave.backend.domainservices.DeviceInformationCache;
import pt.sensae.services.device.management.slave.backend.domainservices.UnhandledSensorDataCache;

@Service
public class DeviceInformationHandlerService {

    private final DeviceInformationCache cache;

    private final UnhandledSensorDataCache unhandledSensorDataCache;

    private final DeviceEventMapper mapper;

    private final SensorDataPublisherService handler;

    public DeviceInformationHandlerService(DeviceInformationCache cache,
                                           UnhandledSensorDataCache unhandledSensorDataCache,
                                           DeviceEventMapper mapper,
                                           SensorDataPublisherService handler) {
        this.cache = cache;
        this.unhandledSensorDataCache = unhandledSensorDataCache;
        this.mapper = mapper;
        this.handler = handler;
    }

    public void info(DeviceNotificationDTO dto) {
        var notification = mapper.dtoToDomain(dto);
        if (notification.type().equals(NotificationType.DELETE)) {
            cache.delete(notification.id());
        } else {
            cache.update(notification.info());
            unhandledSensorDataCache.retrieve(notification.id()).forEach(handler::publish);
        }
    }
}
