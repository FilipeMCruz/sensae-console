package pt.sensae.services.device.commander.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.domain.model.notification.NotificationType;
import pt.sensae.services.device.commander.backend.domainservices.DeviceInformationCache;
import pt.sensae.services.device.commander.backend.domainservices.UnhandledDeviceCommandsCache;

@Service
public class DeviceInformationNotifierService {

    private final DeviceInformationCache cache;

    private final DeviceEventMapper mapper;
    private final UnhandledDeviceCommandsCache unhandledDeviceCommandsCache;

    private final CommandDispatcherService handler;

    public DeviceInformationNotifierService(DeviceInformationCache cache,
                                            DeviceEventMapper mapper,
                                            UnhandledDeviceCommandsCache unhandledDeviceCommandsCache,
                                            CommandDispatcherService handler) {
        this.cache = cache;
        this.mapper = mapper;
        this.unhandledDeviceCommandsCache = unhandledDeviceCommandsCache;
        this.handler = handler;
    }

    public void info(DeviceNotificationDTO dto) {
        var notification = mapper.dtoToDomain(dto);
        if (notification.type().equals(NotificationType.DELETE)) {
            cache.delete(notification.id());
        } else {
            cache.update(notification.info());
            unhandledDeviceCommandsCache.retrieve(notification.id()).forEach(handler::tryToSend);
        }
    }
}
