package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.RecordEraserService;
import sharespot.services.devicerecordsbackend.application.RecordRegisterService;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model.DeviceRecordsDeleteEventDTOImpl;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model.DeviceRecordsIndexEventDTOImpl;

@Service
public class DeviceRecordListener {

    public static final String MASTER_QUEUE = "Sharespot Device Records Master Exchange -> Sharespot Device Records Slave Queue";

    private final RecordEraserService eraser;
    private final RecordRegisterService register;

    public DeviceRecordListener(RecordEraserService eraser, RecordRegisterService register) {
        this.eraser = eraser;
        this.register = register;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveEraseEvent(DeviceRecordsDeleteEventDTOImpl in) {
        eraser.erase(in);
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(DeviceRecordsIndexEventDTOImpl in) {
        register.register(in);
    }
}
