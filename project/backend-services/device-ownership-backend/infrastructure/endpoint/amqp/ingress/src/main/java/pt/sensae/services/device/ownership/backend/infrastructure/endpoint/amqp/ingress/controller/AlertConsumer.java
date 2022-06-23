package pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.ingress.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sensae.services.device.ownership.backend.application.AlertHandlerService;

import java.io.IOException;

@Service
public class AlertConsumer {

    Logger logger = LoggerFactory.getLogger(AlertConsumer.class);

    private final ObjectMapper mapper;

    public final AlertHandlerService handler;

    public AlertConsumer(ObjectMapper mapper, AlertHandlerService handler) {
        this.mapper = mapper;
        this.handler = handler;
    }

    @RabbitListener(queues = "#{queueNamingService.getAlertQueueName()}")
    public void receiveUpdate(Message in) throws IOException {
        var consumed = mapper.readValue(in.getBody(), new TypeReference<MessageConsumed<AlertDTO, AlertRoutingKeys>>() {
        });
        logConsumedMessage(consumed);
        handler.info(consumed);
    }

    private void logConsumedMessage(MessageConsumed<AlertDTO, AlertRoutingKeys> in) {
        logger.info("Alert Id Consumer: {}", in.data.id);
        logger.info("Category: {}", in.data.category);
        logger.info("Sub Category: {}", in.data.subCategory);
    }
}
