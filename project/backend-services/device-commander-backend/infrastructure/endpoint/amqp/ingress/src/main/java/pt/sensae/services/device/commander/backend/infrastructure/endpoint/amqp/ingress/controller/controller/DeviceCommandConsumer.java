package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.ingress.controller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.application.DeviceCommandHandlerService;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.ingress.controller.model.DeviceCommandDTOImpl;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

import java.io.IOException;

@Service
public class DeviceCommandConsumer {

    Logger logger = LoggerFactory.getLogger(DeviceCommandConsumer.class);

    public static final String COMMANDS_QUEUE = "commands.topic -> Device Commander";

    private final DeviceCommandHandlerService handler;
    private final ObjectMapper mapper;


    public DeviceCommandConsumer(DeviceCommandHandlerService handler, ObjectMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    @RabbitListener(queues = COMMANDS_QUEUE)
    public void receiveUpdate(Message in) throws IOException {
        var consumed = mapper.readValue(in.getBody(), DeviceCommandDTOImpl.class);
        handler.publish(consumed);
    }

    private void logConsumedMessage(MessageConsumed<ProcessedSensorDataDTO> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
