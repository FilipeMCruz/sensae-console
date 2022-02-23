package sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.application.UpdateDeviceService;
import sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;

@Service
public class DeviceRecordConsumer {

    public static final String MASTER_QUEUE = "Sharespot Identity Management Master Exchange -> Sharespot Identity Management Slave Queue";

    private final UpdateDeviceService updater;

    public DeviceRecordConsumer(UpdateDeviceService updater) {
        this.updater = updater;
    }
    
    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(DeviceIdDTOImpl in) {
        updater.update(in);
    }
}
