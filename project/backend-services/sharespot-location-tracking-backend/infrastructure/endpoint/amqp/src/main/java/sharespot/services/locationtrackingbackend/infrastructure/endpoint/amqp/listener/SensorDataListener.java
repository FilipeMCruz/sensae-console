package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import sharespot.services.locationtrackingbackend.application.publishers.GPSDataPublisher;
import sharespot.services.locationtrackingbackend.domain.message.MessageConsumed;
import sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto.ProcessedSensorDataDTOImpl;
import sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.mapper.GPSDataMapper;

@Component
public class SensorDataListener {

    Logger logger = LoggerFactory.getLogger(SensorDataListener.class);

    public static final String INGRESS_QUEUE = "Sharespot Location Tracking Queue";

    private final GPSDataPublisher handler;

    public SensorDataListener(GPSDataPublisher handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(MessageConsumed<ProcessedSensorDataDTOImpl> in) {
        logConsumedMessage(in);
        handler.publish(GPSDataMapper.dtoToDomain(in.data));
    }

    private void logConsumedMessage(MessageConsumed<ProcessedSensorDataDTOImpl> in) {
        logger.info("Data Consumed: {}", in.data.dataId);
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
