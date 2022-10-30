package pt.sensae.services.device.commander.application;

import pt.sensae.services.device.commander.application.mapper.DeviceCommandMapper;
import pt.sensae.services.device.commander.application.model.DeviceCommandDTO;
import pt.sensae.services.device.commander.domain.device.Device;
import pt.sensae.services.device.commander.domain.device.DeviceDownlink;
import pt.sensae.services.device.commander.domain.DeviceInformationRepository;
import pt.sensae.services.device.commander.domain.UnHandledDeviceCommandRepository;
import pt.sensae.services.device.commander.domain.device.DeviceName;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DeviceCommandHandlerService {

    @Inject
    DeviceInformationRepository cache;

    @Inject
    UnHandledDeviceCommandRepository unhandledDeviceCommandsCache;

    @Inject
    DeviceCommandMapper mapper;

    @Inject
    CommandDispatcherService dispatcher;

    @Inject
    DeviceNotificationPublisherService notificationPublisher;

    public void publish(DeviceCommandDTO command) {
        var commandReceived = mapper.toModel(command);
        var deviceId = commandReceived.deviceId();
        if (cache.findById(deviceId).isPresent()) {
            dispatcher.tryToSend(mapper.toModel(command));
            notificationPublisher.publishPing(new Device(deviceId, new DeviceName(""), new DeviceDownlink("")));
        } else {
            unhandledDeviceCommandsCache.insert(commandReceived, deviceId);
            notificationPublisher.publishRequest(Device.empty(deviceId));
        }
    }
}
