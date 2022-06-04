package pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.application.notification.NotificationPublisher;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.mapper.AlertNotificationMapper;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

@Service
public class AlertConsumer {

    private final Logger logger = LoggerFactory.getLogger(AlertConsumer.class);

    public static final String QUEUE = "alert.notification.dispatcher.queue";

    private final NotificationPublisher publisher;

    public AlertConsumer(NotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(MessageConsumed<AlertDTO, AlertRoutingKeys> in) {
        logConsumedMessage(in);
        publisher.publish(AlertNotificationMapper.dtoToDomain(in.data));
    }

    private void logConsumedMessage(MessageConsumed<AlertDTO, AlertRoutingKeys> in) {
        logger.info("Alert Id Consumer: {}", in.data.id);
        logger.info("Category: {}", in.data.category);
        logger.info("Sub Category: {}", in.data.subCategory);
    }
}
