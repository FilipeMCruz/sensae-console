package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.stereotype.Service;

@Service
public class TenantIdentityNotificationConsumer {

    public static final String QUEUE = "internal.notification.management.queue";

}
