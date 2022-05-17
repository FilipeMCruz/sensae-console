package sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.application.DeviceOwnershipNotifierService;
import sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;
import sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;

import java.io.IOException;

@Service
public class DeviceDomainsConsumer {

    public static final String QUEUE = "internal.identity.management.slave.queue";

    private final DeviceOwnershipNotifierService updater;

    private final ObjectMapper mapper;

    public DeviceDomainsConsumer(DeviceOwnershipNotifierService updater, ObjectMapper mapper) {
        this.updater = updater;
        this.mapper = mapper;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), DeviceNotificationDTOImpl.class);
        updater.info(dto);
    }
}
