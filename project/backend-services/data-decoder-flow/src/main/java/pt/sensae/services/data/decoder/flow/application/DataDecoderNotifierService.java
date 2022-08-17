package pt.sensae.services.data.decoder.flow.application;


import pt.sensae.services.data.decoder.flow.application.mapper.DataDecoderEventMapper;
import pt.sensae.services.data.decoder.flow.application.model.DataDecoderNotificationDTO;
import pt.sensae.services.data.decoder.flow.domain.DataDecoderRepository;
import pt.sensae.services.data.decoder.flow.domain.NotificationType;
import pt.sensae.services.data.decoder.flow.domain.UnHandledDataUnitRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DataDecoderNotifierService {

    @Inject
    DataDecoderRepository cache;

    @Inject
    UnHandledDataUnitRepository unhandledSensorDataCache;

    @Inject
    DataDecoderEventMapper mapper;

    @Inject
    DataUnitProcessor handler;


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
