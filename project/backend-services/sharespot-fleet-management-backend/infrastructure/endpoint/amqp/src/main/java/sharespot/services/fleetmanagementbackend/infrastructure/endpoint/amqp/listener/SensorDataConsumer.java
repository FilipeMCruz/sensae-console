package sharespot.services.fleetmanagementbackend.infrastructure.endpoint.amqp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.fleetmanagementbackend.application.GPSDataArchiver;
import sharespot.services.fleetmanagementbackend.application.GPSDataPublisher;

@Component
public class SensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    public static final String INGRESS_QUEUE = "Sharespot Location Tracking Queue";

    private final GPSDataArchiver handler;

    public SensorDataConsumer(GPSDataArchiver handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(MessageConsumed<ProcessedSensorDataWithRecordsDTO> in) {
        try {
            logConsumedMessage(in);
            handler.save(in.data);
        } catch (Exception ignore) {
        }
    }

    private void logConsumedMessage(MessageConsumed<ProcessedSensorDataWithRecordsDTO> in) {
        logger.info("Data Consumed: {}", in.data.dataId);
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
