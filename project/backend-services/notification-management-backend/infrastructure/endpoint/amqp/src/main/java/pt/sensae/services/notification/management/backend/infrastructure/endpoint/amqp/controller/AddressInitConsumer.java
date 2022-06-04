package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.addressee.service.AddresseeInitNotificationHandler;

@Service
public class AddressInitConsumer {

    private final AddresseeInitNotificationHandler handler;

    public static final String QUEUE = "internal.notification.management.addressee.sync.queue";

    public AddressInitConsumer(AddresseeInitNotificationHandler handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) {
        this.handler.publishCurrentState();
    }
}
