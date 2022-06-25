package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import pt.sensae.services.device.management.master.backend.application.DeviceCommandTestService;
import pt.sensae.services.device.management.master.backend.application.command.DeviceCommandDTO;
import pt.sharespot.iot.core.IoTCoreTopic;

@Component
public class DeviceCommandEmitter implements DeviceCommandTestService {

    private final AmqpTemplate template;

    private final ObjectMapper mapper;

    private final String routingKeys;

    public DeviceCommandEmitter(AmqpTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
        this.routingKeys = "device";
    }

    @Override
    public void send(DeviceCommandDTO command) {
        try {
            template.convertAndSend(IoTCoreTopic.COMMAND_EXCHANGE, this.routingKeys, mapper.writeValueAsBytes(command));
        } catch (JsonProcessingException ignore) {
        }
    }
}
