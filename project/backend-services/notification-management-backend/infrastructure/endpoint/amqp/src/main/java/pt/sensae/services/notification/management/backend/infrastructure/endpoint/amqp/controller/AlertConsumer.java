package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.notification.service.NotificationPublisher;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.mapper.AlertNotificationMapperImpl;
import pt.sharespot.iot.core.alert.model.AlertDTO;

@Service
public class AlertConsumer {

    Logger logger = LoggerFactory.getLogger(AlertConsumer.class);

    public static final String QUEUE = "alert.notification.management.queue";

    private final NotificationPublisher publisher;

    public AlertConsumer(NotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(AlertDTO in) {
        logConsumedMessage(in);
        publisher.publish(AlertNotificationMapperImpl.dtoToDomain(in));
    }

    private void logConsumedMessage(AlertDTO in) {
        logger.info("Alert Id Consumer: {}", in.id);
        logger.info("Category: {}", in.category);
        logger.info("Sub Category: {}", "TODO");
    }
}
