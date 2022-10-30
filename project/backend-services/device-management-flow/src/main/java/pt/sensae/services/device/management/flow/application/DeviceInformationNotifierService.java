package pt.sensae.services.device.management.flow.application;


import pt.sensae.services.device.management.flow.application.mapper.DeviceEventMapper;
import pt.sensae.services.device.management.flow.application.model.DeviceNotificationDTO;
import pt.sensae.services.device.management.flow.domain.DeviceInformationRepository;
import pt.sensae.services.device.management.flow.domain.UnHandledDataUnitRepository;
import pt.sensae.services.device.management.flow.domain.notification.NotificationType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DeviceInformationNotifierService {

    @Inject
    DeviceInformationRepository cache;

    @Inject
    UnHandledDataUnitRepository unhandledSensorDataCache;

    @Inject
    DeviceEventMapper mapper;

    @Inject
    DataUnitProcessor handler;

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
