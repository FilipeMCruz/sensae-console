package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.stereotype.Service;

@Service
public class AlertConsumer {

    public static final String QUEUE = "alert.notification.management.queue";

}
