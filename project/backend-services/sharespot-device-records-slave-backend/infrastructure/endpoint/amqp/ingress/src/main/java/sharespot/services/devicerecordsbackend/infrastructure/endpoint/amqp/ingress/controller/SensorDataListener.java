package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.application.SensorDataHandlerService;
import sharespot.services.devicerecordsbackend.domain.model.message.MessageConsumed;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.model.ProcessedSensorDataDTOImpl;

@Service
public class SensorDataListener {

    Logger logger = LoggerFactory.getLogger(SensorDataListener.class);

    public static final String INGRESS_QUEUE = "Sharespot Device Records Slave Queue";

    private final SensorDataHandlerService handler;

    public SensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(MessageConsumed<ProcessedSensorDataDTOImpl> in) {
        logConsumedMessage(in);
        handler.publish(in);
    }

    private void logConsumedMessage(MessageConsumed<ProcessedSensorDataDTOImpl> in) {
        logger.info("Data Consumed: {}", in.data.dataId);
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
