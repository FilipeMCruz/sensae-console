package pt.sensae.services.fleet.management.backend.infrastructure.endpoint.amqp.listener;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.mapper.MessageMapper;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import pt.sensae.services.fleet.management.backend.application.GPSDataArchiver;

@Component
public class SensorDataConsumer {

    public static final Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    public static final String INGRESS_QUEUE = "sensor.fleet.management.queue";

    private final GPSDataArchiver handler;

    public SensorDataConsumer(GPSDataArchiver handler) {
        this.handler = handler;
    }


    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(Message in) throws InvalidProtocolBufferException {
        var consumed = MessageMapper.toModel(in.getBody());
        logConsumedMessage(consumed);
        handler.publish(consumed.data);
    }

    private void logConsumedMessage(MessageConsumed<SensorDataDTO, SensorRoutingKeys> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
