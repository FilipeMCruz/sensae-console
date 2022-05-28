package sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.egress.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.MessageSupplied;
import sharespot.services.identitymanagementslavebackend.application.AlertPublisherService;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.alert.AlertMessage;

import javax.annotation.PostConstruct;

@Component
public class AlertSupplier {

    public final Logger logger = LoggerFactory.getLogger(AlertSupplier.class);
    private final AmqpTemplate template;
    private final AlertPublisherService service;
    private final ObjectMapper mapper;

    public AlertSupplier(AmqpTemplate template, AlertPublisherService service, ObjectMapper mapper) {
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
        logger.info("Alert Id: {}", in.data.id);
        logger.info("Alert Context: {}", in.data.context);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
    }
}
