package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.information;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceInformationNotifierService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.device.DeviceDTOImpl;

@Service
public class DeviceInformationRequestConsumer {

    private final DeviceInformationNotifierService informer;

    public DeviceInformationRequestConsumer(DeviceInformationNotifierService informer) {
        this.informer = informer;
    }

    @RabbitListener(queues = "#{queueNamingService.getDeviceManagementRequestQueueName()}")
    public void receiveIndexEvent(DeviceDTOImpl dto) {
        informer.notify(dto);
    }
}
