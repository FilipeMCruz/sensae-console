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
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageSupplied;

import javax.annotation.PostConstruct;

@Component
public class AlertsSupplier {

    Logger logger = LoggerFactory.getLogger(AlertsSupplier.class);
    private final AmqpTemplate template;
    private final AlertHandlerService service;
    private final ObjectMapper mapper;

    public AlertsSupplier(AmqpTemplate template, AlertHandlerService service, ObjectMapper mapper) {
        this.template = template;
        this.service = service;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init() {
        service.getSinglePublisher().subscribe(outData -> {
            logSuppliedMessage(outData);
            try {
                template.send(IoTCoreTopic.ALERT_EXCHANGE, outData.routingKeys
                        .toString(), new Message(mapper.writeValueAsBytes(outData)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void logSuppliedMessage(MessageSupplied<AlertDTO, AlertRoutingKeys> in) {
        logger.info("Alert Category: {}", in.data.category);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
    }
}
