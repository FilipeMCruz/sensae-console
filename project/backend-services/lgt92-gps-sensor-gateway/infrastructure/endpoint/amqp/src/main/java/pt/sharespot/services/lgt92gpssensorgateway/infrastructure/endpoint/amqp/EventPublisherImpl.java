package pt.sharespot.services.lgt92gpssensorgateway.infrastructure.endpoint.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import pt.sharespot.services.lgt92gpssensorgateway.application.EventPublisher;
import pt.sharespot.services.lgt92gpssensorgateway.model.dto.sensor.lgt92sensor.LGT92SensorData;

@Component
public class EventPublisherImpl implements EventPublisher {

    private final AmqpTemplate rabbitTemplate;

    public EventPublisherImpl(AmqpTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(LGT92SensorData eventEmitter) {
        rabbitTemplate.convertAndSend("LGT92 GPS Data Queue", eventEmitter);
    }
}
