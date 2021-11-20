package sharespot.services.lgt92gpsdatagateway.infrastructure.endpoint.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import sharespot.services.lgt92gpsdatagateway.application.EventPublisher;
import sharespot.services.lgt92gpsdatagateway.model.SensorData;

@Controller
public class SensorDataEmitter implements EventPublisher {

    private final AmqpTemplate rabbitTemplate;

    public SensorDataEmitter(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(SensorData eventEmitter) {
        rabbitTemplate.convertAndSend("LGT92 GPS Data Exchange", "", eventEmitter);
    }
}
