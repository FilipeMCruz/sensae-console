package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.identity.device;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.identity.device.DeviceIdentityInitNotificationHandler;

@Service
public class DeviceIdentityInitConsumer {
    public static final String QUEUE = "internal.identity.management.device.init.queue";

    private final DeviceIdentityInitNotificationHandler service;

    public DeviceIdentityInitConsumer(DeviceIdentityInitNotificationHandler service) {
        this.service = service;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) {
        this.service.publishCurrentState();
    }
}
