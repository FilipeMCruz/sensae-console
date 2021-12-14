package sharespot.services.fastdatastore.infrastructure.endpoint.amqp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.fastdatastore.application.SensorDataHandlerService;
import sharespot.services.fastdatastore.application.message.MessageConsumed;
import sharespot.services.fastdatastore.infrastructure.endpoint.amqp.model.ProcessedSensorDataDTOImpl;

@Service
public class SensorDataListener {

    Logger logger = LoggerFactory.getLogger(SensorDataListener.class);

    public static final String QUEUE = "Sharespot Chrono Data Store Queue";

    private final SensorDataHandlerService handler;

    public SensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(MessageConsumed<ProcessedSensorDataDTOImpl> in) {
        logConsumedMessage(in);
        try {
            handler.publish(in.data);
        } catch (Exception exception) {
            logDroppedMessage(in, exception);
        }
    }

    private void logDroppedMessage(MessageConsumed<ProcessedSensorDataDTOImpl> in, Exception exception) {
        logger.warn("Data dropped: {}", in.data.dataId);
        logger.warn("RoutingKeys: {}", in.routingKeys.toString());
        logger.warn(exception.getMessage());
    }

    private void logConsumedMessage(MessageConsumed<ProcessedSensorDataDTOImpl> in) {
        logger.info("Data Id Consumed: {}", in.data.dataId);
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
