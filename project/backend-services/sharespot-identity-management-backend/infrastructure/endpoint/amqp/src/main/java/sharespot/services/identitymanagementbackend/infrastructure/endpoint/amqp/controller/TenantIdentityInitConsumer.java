package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.internal.tenant.TenantInitNotificationHandler;

@Service
public class TenantIdentityInitConsumer {
    public static final String QUEUE = "internal.identity.management.tenant.queue";

    private final TenantInitNotificationHandler service;

    public TenantIdentityInitConsumer(TenantInitNotificationHandler service) {
        this.service = service;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) {
        this.service.publishCurrentState();
    }
}
