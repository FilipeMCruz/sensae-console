package sharespot.services.dataprocessor.infrastructure.endpoint.amqpingress.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import sharespot.services.dataprocessor.application.SensorDataHandlerService;

@Service
public class SensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);
    
    public static final String INGRESS_QUEUE = "Sharespot Data Processor Slave Queue";

    private final SensorDataHandlerService handler;

    public SensorDataConsumer(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(MessageConsumed<ObjectNode> in) {
        logConsumedMessage(in);
        handler.publish(in);
    }

    private void logConsumedMessage(MessageConsumed<ObjectNode> in) {
        logger.info("Data Id Consumed: Unknown");
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
