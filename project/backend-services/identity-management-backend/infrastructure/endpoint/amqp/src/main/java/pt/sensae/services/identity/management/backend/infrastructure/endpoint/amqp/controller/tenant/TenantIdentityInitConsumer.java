package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.tenant;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.tenant.TenantInitNotificationHandler;

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
