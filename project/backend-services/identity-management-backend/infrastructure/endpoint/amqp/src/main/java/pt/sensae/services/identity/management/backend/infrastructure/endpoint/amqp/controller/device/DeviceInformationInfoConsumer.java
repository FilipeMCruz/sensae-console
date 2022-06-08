package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.device;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceInformationNotificationHandler;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.device.DeviceNotificationDTOImpl;


@Service
public class DeviceInformationInfoConsumer {

    public static final String QUEUE = "internal.identity.management.device.info.queue";

    private final DeviceInformationNotificationHandler notifier;


    public DeviceInformationInfoConsumer(DeviceInformationNotificationHandler notifier) {
        this.notifier = notifier;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(DeviceNotificationDTOImpl in) {
        notifier.info(in);
    }
}
