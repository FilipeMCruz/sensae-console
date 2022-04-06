package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sensae.services.smart.irrigation.backend.application.DataHandlerService;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

@Component
public class GardenSensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(GardenSensorDataConsumer.class);

    public static final String INGRESS_QUEUE = "Sharespot Smart Irrigation Queue (park)";

    private final DataHandlerService handler;

    public GardenSensorDataConsumer(DataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(MessageConsumed<ProcessedSensorDataDTO> in) {
        logConsumedMessage(in);
        handler.handle(in.data);
    }

    private void logConsumedMessage(MessageConsumed<ProcessedSensorDataDTO> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
