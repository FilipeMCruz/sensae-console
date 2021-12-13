package sharespot.services.lgt92gpsdatagateway.infrastructure.endpoint.amqp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import sharespot.services.lgt92gpsdatagateway.application.EventPublisher;
import sharespot.services.lgt92gpsdatagateway.application.model.MessageSupplied;

@Controller
public class SensorDataEmitter implements EventPublisher {

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    private final AmqpTemplate rabbitTemplate;

    public SensorDataEmitter(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(MessageSupplied<ObjectNode> message) {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, message.routingKeys.toString(), message);
    }
}
