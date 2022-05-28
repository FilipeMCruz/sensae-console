package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.alert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sensae.services.smart.irrigation.backend.application.services.alert.AlertHandlerService;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ValveStatusType;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import java.io.IOException;

@Component
public class CloseValveAlertConsumer {

    Logger logger = LoggerFactory.getLogger(CloseValveAlertConsumer.class);

    public static final String QUEUE = "alert.smart.irrigation.close.valve.queue";

    private final ObjectMapper mapper;

    private final AlertHandlerService handler;

    public CloseValveAlertConsumer(ObjectMapper mapper, AlertHandlerService handler) {
        this.mapper = mapper;
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) throws IOException {
        var consumed = mapper.readValue(in.getBody(), new TypeReference<MessageConsumed<AlertDTO, AlertRoutingKeys>>() {
        });
        logConsumedMessage(consumed);
        handler.handle(consumed.data, ValveStatusType.CLOSE);
    }

    private void logConsumedMessage(MessageConsumed<AlertDTO, AlertRoutingKeys> in) {
        logger.info("Alert Id Consumer: {}", in.data.id);
        logger.info("Category: {}", in.data.category);
        logger.info("Sub Category: {}", in.data.subCategory);
    }
}
