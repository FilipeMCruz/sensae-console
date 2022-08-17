package pt.sensae.services.device.management.flow.application;

import pt.sensae.services.device.management.flow.domain.DeviceInformationRepository;
import pt.sensae.services.device.management.flow.domain.UnHandledDataUnitRepository;
import pt.sensae.services.device.management.flow.domain.device.Device;
import pt.sensae.services.device.management.flow.domain.device.DeviceDownlink;
import pt.sensae.services.device.management.flow.domain.device.DeviceId;
import pt.sensae.services.device.management.flow.domain.device.DeviceName;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

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

    public void publish(MessageConsumed<SensorDataDTO, SensorRoutingKeys> message) {
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
