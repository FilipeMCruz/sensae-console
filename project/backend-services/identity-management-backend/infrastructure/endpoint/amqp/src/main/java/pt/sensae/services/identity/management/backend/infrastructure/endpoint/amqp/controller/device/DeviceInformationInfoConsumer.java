package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceInformationNotificationHandler;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.device.DeviceNotificationDTOImpl;

import java.io.IOException;

@Service
public class DeviceInformationInfoConsumer {

    public static final String QUEUE = "internal.identity.management.device.info.queue";

    private final DeviceInformationNotificationHandler notifier;

    private final ObjectMapper mapper;

    public DeviceInformationInfoConsumer(DeviceInformationNotificationHandler notifier, ObjectMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), DeviceNotificationDTOImpl.class);
        notifier.info(dto);
    }
}
