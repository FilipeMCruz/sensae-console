package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.internal.DeviceInformationNotifierService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model.DeviceIdDTOImpl;

@Service
public class DeviceNotificationConsumer {

    public static final String MASTER_QUEUE = "internal.identity.management.master.queue";

    private final DeviceInformationNotifierService informer;

    public DeviceNotificationConsumer(DeviceInformationNotifierService informer) {
        this.informer = informer;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(DeviceIdDTOImpl dto) {
        informer.notify(dto);
    }
}
