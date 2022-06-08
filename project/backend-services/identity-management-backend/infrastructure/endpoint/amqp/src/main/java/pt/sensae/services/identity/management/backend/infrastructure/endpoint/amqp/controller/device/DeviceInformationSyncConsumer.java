package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.device;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceInformationNotificationHandler;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.device.DeviceNotificationDTOImpl;

import java.util.Set;

@Service
public class DeviceInformationSyncConsumer {

    public static final String QUEUE = "internal.identity.management.device.sync.queue";

    private final DeviceInformationNotificationHandler notifier;

    public DeviceInformationSyncConsumer(DeviceInformationNotificationHandler notifier) {
        this.notifier = notifier;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(Set<DeviceNotificationDTOImpl> in) {
        notifier.sync(in.stream().map(d -> d));
    }
}
