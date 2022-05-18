package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.egress.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import pt.sensae.services.smart.irrigation.backend.application.services.command.DeviceCommandPublisher;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.egress.model.DeviceCommandDTOImpl;
import pt.sharespot.iot.core.IoTCoreTopic;

@Component
public class DeviceCommandSupplier {

    Logger logger = LoggerFactory.getLogger(DeviceCommandSupplier.class);

    public DeviceCommandSupplier(AmqpTemplate template, ObjectMapper mapper, DeviceCommandPublisher service) {
        service.getPublisher().subscribe(command -> {
            try {
                template.convertAndSend(IoTCoreTopic.COMMAND_EXCHANGE, "valve", mapper.writeValueAsBytes(command));
                logSuppliedCommand((DeviceCommandDTOImpl) command);
            } catch (JsonProcessingException ignore) {
            }
        });
    }

    private void logSuppliedCommand(DeviceCommandDTOImpl in) {
        logger.info("Device Id Supplied: {}", in.deviceId);
        logger.info("Command Id: {}", in.commandId);
    }
}
