package pt.sensae.services.device.commander.application;


import pt.sensae.services.device.commander.application.mapper.DeviceEventMapper;
import pt.sensae.services.device.commander.application.model.DeviceNotificationDTO;
import pt.sensae.services.device.commander.domain.UnHandledDeviceCommandRepository;
import pt.sensae.services.device.commander.domain.notification.NotificationType;
import pt.sensae.services.device.commander.domain.DeviceInformationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DeviceInformationNotifierService {

    @Inject
    DeviceInformationRepository cache;

    @Inject
    UnHandledDeviceCommandRepository unhandledSensorDataCache;

    @Inject
    DeviceEventMapper mapper;

    @Inject
    CommandDispatcherService handler;

    public void info(DeviceNotificationDTO dto) {
        var notification = mapper.dtoToDomain(dto);
        if (notification.type().equals(NotificationType.DELETE)) {
            cache.delete(notification.id());
        } else {
            cache.update(notification.info());
            unhandledSensorDataCache.retrieve(notification.id()).forEach(handler::tryToSend);
        }
    }
}
