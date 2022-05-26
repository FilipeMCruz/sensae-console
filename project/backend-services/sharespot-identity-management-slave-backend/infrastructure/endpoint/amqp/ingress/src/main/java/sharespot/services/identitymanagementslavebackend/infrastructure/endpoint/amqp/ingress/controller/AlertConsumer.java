package sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.ingress.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import sharespot.services.identitymanagementslavebackend.application.AlertHandlerService;

import java.io.IOException;

@Service
public class AlertConsumer {
    
    Logger logger = LoggerFactory.getLogger(AlertConsumer.class);

    public static final String QUEUE = "alert.identity.management.slave.queue";

    private final ObjectMapper mapper;

    public final AlertHandlerService handler;

    public AlertConsumer(ObjectMapper mapper, AlertHandlerService handler) {
        this.mapper = mapper;
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) throws IOException {
        var consumed = mapper.readValue(in.getBody(), AlertDTO.class);
        logConsumedMessage(consumed);
        handler.info(consumed);
    }

    private void logConsumedMessage(AlertDTO in) {
        logger.info("Alert Id: {}", in.id);
        logger.info("Alert Category: {}", in.category);
    }
}
