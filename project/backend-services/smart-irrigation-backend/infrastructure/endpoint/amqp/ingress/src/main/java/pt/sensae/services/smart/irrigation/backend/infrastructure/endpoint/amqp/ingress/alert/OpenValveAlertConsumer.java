package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.alert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sensae.services.smart.irrigation.backend.application.services.alert.AlertHandlerService;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ValveStatusType;
import pt.sharespot.iot.core.alert.model.AlertDTO;

import java.io.IOException;

@Component
public class OpenValveAlertConsumer {

    Logger logger = LoggerFactory.getLogger(OpenValveAlertConsumer.class);

    public static final String QUEUE = "alert.smart.irrigation.open.valve.queue";

    private final ObjectMapper mapper;

    private final AlertHandlerService handler;

    public OpenValveAlertConsumer(ObjectMapper mapper, AlertHandlerService handler) {
        this.mapper = mapper;
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) throws IOException {
        var consumed = mapper.readValue(in.getBody(), AlertDTO.class);
        logConsumedMessage(consumed);
        handler.handle(consumed, ValveStatusType.OPEN);
    }

    private void logConsumedMessage(AlertDTO in) {
        logger.info("Alert Id: {}", in.id);
        logger.info("Alert Context: {}", in.context);
    }
}
