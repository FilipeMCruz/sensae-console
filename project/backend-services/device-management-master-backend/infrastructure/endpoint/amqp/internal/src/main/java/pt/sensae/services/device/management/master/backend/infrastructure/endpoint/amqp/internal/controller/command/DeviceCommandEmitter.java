package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.command;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sensae.services.device.management.master.backend.application.DeviceCommandTestService;
import pt.sensae.services.device.management.master.backend.application.DeviceCommandDTO;
import pt.sharespot.iot.core.IoTCoreTopic;

@Component
public class DeviceCommandEmitter implements DeviceCommandTestService {

    private final AmqpTemplate template;

    private final String routingKeys;

    public DeviceCommandEmitter(@Qualifier("amqpTemplate") AmqpTemplate template) {
        this.template = template;
        this.routingKeys = "device";
    }

    @Override
    public void send(DeviceCommandDTO command) {
        template.convertAndSend(IoTCoreTopic.COMMAND_EXCHANGE, this.routingKeys, command);
    }
}
