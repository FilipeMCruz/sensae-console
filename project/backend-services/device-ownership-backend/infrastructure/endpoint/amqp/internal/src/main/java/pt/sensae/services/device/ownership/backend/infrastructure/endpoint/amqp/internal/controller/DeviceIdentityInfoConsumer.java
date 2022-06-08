package pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.ownership.backend.application.DeviceOwnershipNotifierService;
import pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;

import java.io.IOException;

@Service
public class DeviceIdentityInfoConsumer {

    public static final String QUEUE = "internal.identity.management.slave.queue";

    private final DeviceOwnershipNotifierService updater;

    private final ObjectMapper mapper;

    public DeviceIdentityInfoConsumer(DeviceOwnershipNotifierService updater, ObjectMapper mapper) {
        this.updater = updater;
        this.mapper = mapper;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), DeviceNotificationDTOImpl.class);
        updater.info(dto);
    }
}
