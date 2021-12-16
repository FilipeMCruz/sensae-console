package sharespot.services.fastdatastore.infrastructure.endpoint.amqp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.fastdatastore.application.SensorDataHandlerService;

@Service
public class SensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    public static final String QUEUE = "Sharespot Chrono Data Store Queue";

    private final SensorDataHandlerService handler;

    public SensorDataConsumer(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(MessageConsumed<ProcessedSensorDataDTO> in) {
        logConsumedMessage(in);
        try {
            handler.publish(in.data);
        } catch (Exception exception) {
            logDroppedMessage(in, exception);
        }
    }

    private void logDroppedMessage(MessageConsumed<ProcessedSensorDataDTO> in, Exception exception) {
        logger.warn("Data dropped: {}", in.data.dataId);
        logger.warn("RoutingKeys: {}", in.routingKeys.toString());
        logger.warn(exception.getMessage());
    }

    private void logConsumedMessage(MessageConsumed<ProcessedSensorDataDTO> in) {
        logger.info("Data Id Consumed: {}", in.data.dataId);
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
