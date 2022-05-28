package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.internal.tenant.TenantNotificationHandler;

@Service
public class TenantNotificationConsumer {
    public static final String QUEUE = "internal.identity.management.tenant.queue";

    private final TenantNotificationHandler service;

    public TenantNotificationConsumer(TenantNotificationHandler service) {
        this.service = service;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) {
        this.service.publishCurrentState();
    }
}
