package sharespot.services.lgt92gpsdatagateway.infrastructure.endpoint.amqp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import pt.sharespot.iot.core.routing.MessageSupplied;
import sharespot.services.lgt92gpsdatagateway.application.EventPublisher;

@Controller
public class SensorDataSupplier implements EventPublisher {

    Logger logger = LoggerFactory.getLogger(SensorDataSupplier.class);

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    private final AmqpTemplate rabbitTemplate;

    public SensorDataSupplier(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(MessageSupplied<ObjectNode> message) {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, message.routingKeys.toString(), message);
        logSuppliedMessage(message);
    }

    private void logSuppliedMessage(MessageSupplied<ObjectNode> in) {
        logger.info("Data Id Supplied: Unknown");
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
