package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceInformationNotifierService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.DeviceDTOImpl;

@Service
public class DeviceNotificationConsumer {

    public static final String MASTER_QUEUE = "internal.device.management.master.queue";

    private final DeviceInformationNotifierService informer;

    public DeviceNotificationConsumer(DeviceInformationNotifierService informer) {
        this.informer = informer;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(DeviceDTOImpl dto) {
        informer.notify(dto);
    }
}
