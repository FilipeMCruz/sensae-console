package pt.sharespot.services.lgt92gpssensorgateway.infrastructure.endpoint.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import pt.sharespot.services.lgt92gpssensorgateway.application.EventPublisher;
import pt.sharespot.services.lgt92gpssensorgateway.model.dto.sensor.lgt92sensor.LGT92SensorData;

@Component
public class LGT92GPSSensorDataEmitter implements EventPublisher {

    private final AmqpTemplate rabbitTemplate;

    public LGT92GPSSensorDataEmitter(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(LGT92SensorData eventEmitter) {
        rabbitTemplate.convertAndSend("LGT92 GPS Data Exchange", "", eventEmitter);
    }
}
