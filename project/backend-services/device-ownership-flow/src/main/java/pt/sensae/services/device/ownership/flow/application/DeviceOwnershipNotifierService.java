package pt.sensae.services.device.ownership.flow.application;


import pt.sensae.services.device.ownership.flow.application.mapper.DeviceNotificationEventMapper;
import pt.sensae.services.device.ownership.flow.application.model.OwnershipNotificationDTO;
import pt.sensae.services.device.ownership.flow.domain.DeviceOwnershipRepository;
import pt.sensae.services.device.ownership.flow.domain.UnHandledDataUnitRepository;
import pt.sensae.services.device.ownership.flow.domain.NotificationType;
import pt.sensae.services.device.ownership.flow.infrastructure.persistence.memory.UnhandledAlertCache;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DeviceOwnershipNotifierService {

    @Inject
    DeviceOwnershipRepository cache;

    @Inject
    UnHandledDataUnitRepository unhandledSensorDataCache;

    @Inject
    UnhandledAlertCache unhandledAlertCache;

    @Inject
    DeviceNotificationEventMapper mapper;

    @Inject
    DataUnitProcessor handler;

    @Inject
    AlertProcessor alertHandler;

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
