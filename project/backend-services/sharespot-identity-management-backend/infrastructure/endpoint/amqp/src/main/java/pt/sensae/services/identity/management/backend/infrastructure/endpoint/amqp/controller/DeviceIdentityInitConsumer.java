package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceInitNotificationHandler;

@Service
public class DeviceIdentityInitConsumer {
    public static final String QUEUE = "internal.identity.management.device.queue";

    private final DeviceInitNotificationHandler service;

    public DeviceIdentityInitConsumer(DeviceInitNotificationHandler service) {
        this.service = service;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) {
        this.service.publishCurrentState();
    }
}
