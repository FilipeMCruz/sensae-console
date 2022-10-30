package pt.sensae.services.device.ownership.flow.application;

import pt.sensae.services.device.ownership.flow.domain.DeviceId;
import pt.sensae.services.device.ownership.flow.domain.DeviceOwnershipRepository;
import pt.sensae.services.device.ownership.flow.domain.UnHandledDataUnitRepository;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.RoutingKeys;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DataUnitHandlerService {

    @Inject
    DeviceOwnershipRepository cache;

    @Inject
    UnHandledDataUnitRepository unhandledSensorDataCache;

    @Inject
    DataUnitProcessor dataPublisher;

    @Inject
    DeviceOwnershipNotificationPublisherService notificationPublisher;

    public void publish(MessageConsumed<DataUnitDTO, DataRoutingKeys> message) {
        var deviceId = new DeviceId(message.data.device.id);
        if (cache.findById(deviceId).isPresent()) {
            dataPublisher.publish(message);
            notificationPublisher.publishPing(deviceId);
        } else {
            unhandledSensorDataCache.insert(message, deviceId);
            notificationPublisher.publishRequest(deviceId);
        }
    }
}
