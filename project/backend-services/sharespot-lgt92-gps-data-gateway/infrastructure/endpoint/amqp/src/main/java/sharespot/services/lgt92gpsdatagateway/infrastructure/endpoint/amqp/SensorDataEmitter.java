package sharespot.services.lgt92gpsdatagateway.infrastructure.endpoint.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import sharespot.services.lgt92gpsdatagateway.application.EventPublisher;

@Controller
public class SensorDataEmitter implements EventPublisher {

    public static final String EGRESS_EXCHANGE = "Sharespot LGT92 GPS Data Gateway Exchange";
    
    private final AmqpTemplate rabbitTemplate;

    public SensorDataEmitter(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(Object eventEmitter) {
        rabbitTemplate.convertAndSend(EGRESS_EXCHANGE, "", eventEmitter);
    }
}
