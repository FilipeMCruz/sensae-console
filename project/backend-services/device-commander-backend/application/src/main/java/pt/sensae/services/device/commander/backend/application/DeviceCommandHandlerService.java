package pt.sensae.services.device.commander.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.domain.model.device.Device;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceName;
import pt.sensae.services.device.commander.backend.domainservices.DeviceInformationCache;
import pt.sensae.services.device.commander.backend.domainservices.UnhandledDeviceCommandsCache;

@Service
public class DeviceCommandHandlerService {
    private final CommandDispatcherService dispatcher;

    private final DeviceCommandMapper mapper;

    private final DeviceInformationCache cache;

    private final UnhandledDeviceCommandsCache unhandledDeviceCommandsCache;

    private final DeviceCommandNotificationPublisherService notificationPublisherService;

    public DeviceCommandHandlerService(CommandDispatcherService dispatcher,
                                       DeviceCommandMapper mapper,
                                       DeviceInformationCache cache,
                                       UnhandledDeviceCommandsCache unhandledDeviceCommandsCache,
                                       DeviceCommandNotificationPublisherService notificationPublisherService) {
        this.dispatcher = dispatcher;
        this.mapper = mapper;
        this.cache = cache;
        this.unhandledDeviceCommandsCache = unhandledDeviceCommandsCache;
        this.notificationPublisherService = notificationPublisherService;
    }

    public void publish(DeviceCommandDTO command) {
        var commandReceived = mapper.toModel(command);
        var deviceId = commandReceived.deviceId();
        if (cache.findById(deviceId).isPresent()) {
            dispatcher.tryToSend(mapper.toModel(command));
        } else {
            unhandledDeviceCommandsCache.insert(commandReceived, deviceId);
            notificationPublisherService.publishRequest(new Device(deviceId, new DeviceName(""), new DeviceDownlink("")));
        }
    }
}
