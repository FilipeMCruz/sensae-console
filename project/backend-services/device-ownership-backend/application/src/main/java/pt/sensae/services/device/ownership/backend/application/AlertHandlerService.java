package pt.sensae.services.device.ownership.backend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sensae.services.device.ownership.backend.domain.device.DeviceId;
import pt.sensae.services.device.ownership.backend.domainservices.DeviceDomainCache;
import pt.sensae.services.device.ownership.backend.domainservices.UnhandledAlertCache;

@Service
public class AlertHandlerService {

    private final DeviceDomainCache cache;

    private final UnhandledAlertCache unhandledAlertCache;

    private final AlertPublisherService dataPublisher;

    private final SensorDataNotificationPublisherService notificationPublisher;

    public AlertHandlerService(DeviceDomainCache cache,
                               UnhandledAlertCache unhandledAlertCache,
                               AlertPublisherService handler,
                               SensorDataNotificationPublisherService notificationPublisher) {
        this.cache = cache;
        this.unhandledAlertCache = unhandledAlertCache;
        this.dataPublisher = handler;
        this.notificationPublisher = notificationPublisher;
    }

    public void info(MessageConsumed<AlertDTO, AlertRoutingKeys> message) {
        var deviceIds = message.data.context.deviceIds.stream().map(DeviceId::of).toList();
        if (cache.findAllById(deviceIds.stream()).isPresent()) {
            dataPublisher.publish(message);
            deviceIds.forEach(notificationPublisher::publishPing);
        } else {
            deviceIds.forEach(deviceId -> {
                if (cache.findById(deviceId).isEmpty()) {
                    unhandledAlertCache.insert(message, deviceId);
                    notificationPublisher.publishRequest(deviceId);
                }
            });
        }
    }
}
