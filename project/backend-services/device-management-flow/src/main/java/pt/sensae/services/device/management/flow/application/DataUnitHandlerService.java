package pt.sensae.services.device.management.flow.application;

import pt.sensae.services.device.management.flow.domain.DeviceInformationRepository;
import pt.sensae.services.device.management.flow.domain.UnHandledDataUnitRepository;
import pt.sensae.services.device.management.flow.domain.device.Device;
import pt.sensae.services.device.management.flow.domain.device.DeviceDownlink;
import pt.sensae.services.device.management.flow.domain.device.DeviceId;
import pt.sensae.services.device.management.flow.domain.device.DeviceName;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.RoutingKeys;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DataUnitHandlerService {

    @Inject
    DeviceInformationRepository cache;

    @Inject
    UnHandledDataUnitRepository unhandledSensorDataCache;

    @Inject
    DataUnitProcessor dataPublisher;

    @Inject
    DataUnitNotificationPublisherService notificationPublisher;

    public void publish(MessageConsumed<DataUnitDTO, DataRoutingKeys> message) {
        var deviceId = new DeviceId(message.data.device.id);
        var device = new Device(deviceId, new DeviceName(message.data.device.name), new DeviceDownlink(message.data.device.downlink));
        if (cache.findById(deviceId).isPresent()) {
            dataPublisher.publish(message);
            notificationPublisher.publishPing(device);
        } else {
            unhandledSensorDataCache.insert(message, deviceId);
            notificationPublisher.publishRequest(device);
        }
    }
}
