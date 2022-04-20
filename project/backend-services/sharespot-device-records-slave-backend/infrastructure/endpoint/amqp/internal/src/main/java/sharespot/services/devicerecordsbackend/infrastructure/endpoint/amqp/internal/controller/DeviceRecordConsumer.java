package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.DeviceIdDTO;
import sharespot.services.devicerecordsbackend.application.RecordRegisterService;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model.DeviceIdDTOImpl;

import java.io.IOException;

@Service
public class DeviceRecordConsumer {

    public static final String MASTER_QUEUE = "Sharespot Device Records Master Exchange -> Sharespot Device Records Slave Queue";

    private final RecordRegisterService updater;

    private final ObjectMapper mapper;

    public DeviceRecordConsumer(RecordRegisterService updater, ObjectMapper mapper) {
        this.updater = updater;
        this.mapper = mapper;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), DeviceIdDTOImpl.class);
        updater.update(dto);
    }
}
