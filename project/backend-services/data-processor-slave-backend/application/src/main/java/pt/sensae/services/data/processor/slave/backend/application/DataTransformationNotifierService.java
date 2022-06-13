package pt.sensae.services.data.processor.slave.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.slave.backend.domain.NotificationType;
import pt.sensae.services.data.processor.slave.backend.domainservices.DataTransformationCache;
import pt.sensae.services.data.processor.slave.backend.domainservices.UnhandledSensorDataCache;

@Service
public class DataTransformationNotifierService {

    private final DataTransformationCache cache;

    private final UnhandledSensorDataCache unhandledSensorDataCache;

    private final DataTransformationEventMapper mapper;

    private final SensorDataPublisherService handler;

    public DataTransformationNotifierService(DataTransformationCache cache,
                                             UnhandledSensorDataCache unhandledSensorDataCache,
                                             DataTransformationEventMapper mapper, SensorDataPublisherService handler) {
        this.cache = cache;
        this.unhandledSensorDataCache = unhandledSensorDataCache;
        this.mapper = mapper;
        this.handler = handler;
    }

    public void info(DataTransformationNotificationDTO dto) {
        var notification = mapper.dtoToDomain(dto);
        if (notification.type().equals(NotificationType.DELETE)) {
            cache.delete(notification.id());
        } else {
            cache.update(notification.information());
            unhandledSensorDataCache.retrieve(notification.id()).forEach(handler::publish);
        }
    }
}
