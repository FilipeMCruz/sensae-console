package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.information;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceInformationNotifierService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.DeviceDTOImpl;

@Service
public class DeviceInformationRequestConsumer {

    public static final String QUEUE = "internal.device.management.device.request.queue";

    private final DeviceInformationNotifierService informer;

    public DeviceInformationRequestConsumer(DeviceInformationNotifierService informer) {
        this.informer = informer;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(DeviceDTOImpl dto) {
        informer.notify(dto);
    }
}
