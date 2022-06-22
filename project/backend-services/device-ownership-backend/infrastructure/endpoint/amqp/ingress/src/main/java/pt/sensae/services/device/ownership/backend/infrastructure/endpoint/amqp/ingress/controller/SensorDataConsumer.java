package pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.ingress.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.mapper.MessageMapper;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import pt.sensae.services.device.ownership.backend.application.SensorDataHandlerService;

@Service
public class SensorDataConsumer {

    private final Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    private final SensorDataHandlerService handler;

    public SensorDataConsumer(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = "#{queueNamingService.getDeviceQueueName()}")
    public void receiveUpdate(Message in) throws InvalidProtocolBufferException {
        var consumed = MessageMapper.toModel(in.getBody());
        logConsumedMessage(consumed);
        handler.info(consumed);
    }

    private void logConsumedMessage(MessageConsumed<SensorDataDTO, SensorRoutingKeys> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
