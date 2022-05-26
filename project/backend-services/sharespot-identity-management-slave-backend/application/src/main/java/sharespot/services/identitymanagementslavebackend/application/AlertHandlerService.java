package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domainservices.DeviceDomainCache;
import sharespot.services.identitymanagementslavebackend.domainservices.UnhandledAlertCache;

import java.util.UUID;
import java.util.stream.Stream;

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

    public void info(AlertDTO message) {
        var deviceIds = message.context.deviceIds.stream().map(DeviceId::of).toList();
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