package pt.sensae.services.data.processor.flow.application;


import pt.sensae.services.data.processor.flow.application.mapper.DataTransformationEventMapper;
import pt.sensae.services.data.processor.flow.application.model.DataTransformationNotificationDTO;
import pt.sensae.services.data.processor.flow.domain.DataProcessorRepository;
import pt.sensae.services.data.processor.flow.domain.NotificationType;
import pt.sensae.services.data.processor.flow.domain.UnHandledDataUnitRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DataTransformationNotifierService {

    @Inject
    DataProcessorRepository cache;

    @Inject
    UnHandledDataUnitRepository unhandledSensorDataCache;

    @Inject
    DataTransformationEventMapper mapper;

    @Inject
    DataUnitProcessor handler;


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
