package pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.egress.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import pt.sensae.services.alert.dispatcher.backend.application.AlertHandlerService;
import pt.sensae.services.alert.dispatcher.backend.domain.AlertMessage;
import pt.sharespot.iot.core.IoTCoreTopic;

@Component
public class AlertsSupplier {

    Logger logger = LoggerFactory.getLogger(AlertsSupplier.class);

    public AlertsSupplier(AmqpTemplate template, AlertHandlerService service, ObjectMapper mapper) {
        service.getSinglePublisher().subscribe(outData -> {
            logSuppliedMessage(outData);
            try {
                template.send(IoTCoreTopic.ALERT_EXCHANGE, outData.routingKeys()
                        .toString(), new Message(mapper.writeValueAsBytes(outData.alert())));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void logSuppliedMessage(AlertMessage in) {
        logger.info("Alert Category: {}", in.alert().category);
        logger.info("RoutingKeys: {}", in.routingKeys().details());
    }
}
