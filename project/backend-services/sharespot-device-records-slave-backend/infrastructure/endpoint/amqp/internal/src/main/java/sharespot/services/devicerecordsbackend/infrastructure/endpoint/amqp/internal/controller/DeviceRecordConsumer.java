package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.RecordRegisterService;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;

@Service
public class DeviceRecordConsumer {

    public static final String MASTER_QUEUE = "Sharespot Device Records Master Exchange -> Sharespot Device Records Slave Queue";

    private final RecordRegisterService updater;

    public DeviceRecordConsumer(RecordRegisterService updater) {
        this.updater = updater;
    }
    
    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(DeviceIdDTOImpl in) {
        updater.update(in);
    }
}
