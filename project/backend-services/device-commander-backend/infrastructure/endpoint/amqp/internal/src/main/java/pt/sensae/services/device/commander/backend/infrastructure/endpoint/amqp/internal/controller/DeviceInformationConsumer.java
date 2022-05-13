package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.application.DeviceInformationNotifierService;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;

import java.io.IOException;

@Service
public class DeviceInformationConsumer {

    public static final String QUEUE = "internal.device.commander.queue";

    private final DeviceInformationNotifierService notifier;

    private final ObjectMapper mapper;

    public DeviceInformationConsumer(DeviceInformationNotifierService notifier, ObjectMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), DeviceNotificationDTOImpl.class);
        notifier.info(dto);
    }
}
