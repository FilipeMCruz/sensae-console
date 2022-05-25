package sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.egress.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.IoTCoreTopic;
import sharespot.services.identitymanagementslavebackend.application.AlertPublisherService;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.alert.AlertMessage;

@Component
public class AlertSupplier {

    public final Logger logger = LoggerFactory.getLogger(AlertSupplier.class);

    public AlertSupplier(AmqpTemplate template, AlertPublisherService service, ObjectMapper mapper) {
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
        logger.info("Alert Context: {}", in.alert().context);
        logger.info("RoutingKeys: {}", in.routingKeys().details());
    }
}
