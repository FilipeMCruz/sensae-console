package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.identity.device;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.identity.device.DeviceIdentityNotifierService;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.identity.device.DeviceIdDTOImpl;

import java.util.UUID;

@Service
public class DeviceIdentityRequestConsumer {

    public static final String MASTER_QUEUE = "internal.identity.management.master.queue";

    private final DeviceIdentityNotifierService informer;

    public DeviceIdentityRequestConsumer(DeviceIdentityNotifierService informer) {
        this.informer = informer;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(DeviceIdDTOImpl dto) {
        informer.notify(new DeviceId(UUID.fromString(dto.id)));
    }
}
