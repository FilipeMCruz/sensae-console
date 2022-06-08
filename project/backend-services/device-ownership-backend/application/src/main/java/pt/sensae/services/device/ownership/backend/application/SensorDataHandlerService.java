package pt.sensae.services.device.ownership.backend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import pt.sensae.services.device.ownership.backend.domain.device.DeviceId;
import pt.sensae.services.device.ownership.backend.domainservices.DeviceDomainCache;
import pt.sensae.services.device.ownership.backend.domainservices.UnhandledSensorDataCache;

@Service
public class SensorDataHandlerService {

    private final DeviceDomainCache cache;

    private final UnhandledSensorDataCache unhandledSensorDataCache;

    private final SensorDataPublisherService dataPublisher;

    private final SensorDataNotificationPublisherService notificationPublisher;

    public SensorDataHandlerService(DeviceDomainCache cache,
                                    UnhandledSensorDataCache unhandledSensorDataCache,
                                    SensorDataPublisherService handler,
                                    SensorDataNotificationPublisherService notificationPublisher) {
        this.cache = cache;
        this.unhandledSensorDataCache = unhandledSensorDataCache;
        this.dataPublisher = handler;
        this.notificationPublisher = notificationPublisher;
    }

    public void info(MessageConsumed<SensorDataDTO, SensorRoutingKeys> message) {
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