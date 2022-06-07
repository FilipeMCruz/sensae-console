package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.information;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceInitNotificationHandler;

@Service
public class DeviceInformationInitConsumer {

    public static final String QUEUE = "internal.device.management.device.init.queue";

    private final DeviceInitNotificationHandler service;

    public DeviceInformationInitConsumer(DeviceInitNotificationHandler service) {
        this.service = service;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) {
        this.service.publishCurrentState();
    }
}
