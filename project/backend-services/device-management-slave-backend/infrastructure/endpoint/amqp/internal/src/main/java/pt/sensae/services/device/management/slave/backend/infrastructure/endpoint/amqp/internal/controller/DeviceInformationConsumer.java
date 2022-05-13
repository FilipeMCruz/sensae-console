package pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.application.DeviceInformationHandlerService;
import pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.model.DeviceInformationDTOImpl;
import pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;

import java.io.IOException;

@Service
public class DeviceInformationConsumer {

    public static final String MASTER_QUEUE = "internal.device.management.slave.queue";

    private final DeviceInformationHandlerService informer;

    private final ObjectMapper mapper;

    public DeviceInformationConsumer(DeviceInformationHandlerService updater, ObjectMapper mapper) {
        this.informer = updater;
        this.mapper = mapper;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), DeviceNotificationDTOImpl.class);
        informer.info(dto);
    }
}
