package pt.sensae.services.device.ownership.flow.application;

import pt.sensae.services.device.ownership.flow.domain.DeviceId;
import pt.sensae.services.device.ownership.flow.domain.DeviceOwnershipRepository;
import pt.sensae.services.device.ownership.flow.domain.UnhandledAlertRepository;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlertHandlerService {

    @Inject
    DeviceOwnershipRepository cache;

    @Inject
    UnhandledAlertRepository unhandledAlertCache;

    @Inject
    AlertProcessor dataPublisher;

    @Inject
    DeviceOwnershipNotificationPublisherService notificationPublisher;

    public void info(MessageConsumed<AlertDTO, AlertRoutingKeys> message) {
        var deviceIds = message.data.context.deviceIds.stream().map(DeviceId::of).collect(Collectors.toList());
        if (deviceIds.isEmpty()) {
            var root = DeviceId.root();
            deviceIds.add(root);
            message.data.context.deviceIds.add(root.value());
        }

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
