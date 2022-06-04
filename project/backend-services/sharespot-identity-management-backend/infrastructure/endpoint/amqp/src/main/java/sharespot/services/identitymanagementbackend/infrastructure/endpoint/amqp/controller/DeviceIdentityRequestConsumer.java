package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.internal.device.DeviceInformationNotifierService;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model.DeviceIdDTOImpl;

import java.util.UUID;

@Service
public class DeviceIdentityRequestConsumer {

    public static final String MASTER_QUEUE = "internal.identity.management.master.queue";

    private final DeviceInformationNotifierService informer;

    public DeviceIdentityRequestConsumer(DeviceInformationNotifierService informer) {
        this.informer = informer;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(DeviceIdDTOImpl dto) {
        informer.notify(new DeviceId(UUID.fromString(dto.id)));
    }
}