package pt.sensae.services.data.decoder.slave.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.slave.backend.domain.NotificationType;
import pt.sensae.services.data.decoder.slave.backend.domainservices.DataDecoderCache;
import pt.sensae.services.data.decoder.slave.backend.domainservices.UnhandledSensorDataCache;

@Service
public class DataDecoderNotifierService {

    private final DataDecoderCache cache;

    private final UnhandledSensorDataCache unhandledSensorDataCache;

    private final DataDecoderEventMapper mapper;

    private final SensorDataPublisherService handler;

    public DataDecoderNotifierService(DataDecoderCache cache,
                                      UnhandledSensorDataCache unhandledSensorDataCache,
                                      DataDecoderEventMapper mapper,
                                      SensorDataPublisherService handler) {
        this.cache = cache;
        this.unhandledSensorDataCache = unhandledSensorDataCache;
        this.mapper = mapper;
        this.handler = handler;
    }

    public void info(DataDecoderNotificationDTO dto) {
        var notification = mapper.dtoToDomain(dto);
        if (notification.type().equals(NotificationType.DELETE)) {
            cache.delete(notification.id());
        } else {
            cache.update(notification.information());
            unhandledSensorDataCache.retrieve(notification.id()).forEach(handler::publish);
        }
    }
}
