package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.information;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceLastTimeSeenRegisterService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.device.DeviceDTOImpl;

@Service
public class DeviceInformationPingConsumer {

    private final DeviceLastTimeSeenRegisterService lastTimeSeenRegistry;

    public DeviceInformationPingConsumer(DeviceLastTimeSeenRegisterService lastTimeSeenRegistry) {
        this.lastTimeSeenRegistry = lastTimeSeenRegistry;
    }

    @RabbitListener(queues = "#{queueNamingService.getDeviceManagementPingQueueName()}")
    public void receiveIndexEvent(DeviceDTOImpl dto) {
        lastTimeSeenRegistry.updateLastTimeSeen(dto);
    }
}
