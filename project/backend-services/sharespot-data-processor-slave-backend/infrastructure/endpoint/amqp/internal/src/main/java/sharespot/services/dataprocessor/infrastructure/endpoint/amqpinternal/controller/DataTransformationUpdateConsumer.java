package sharespot.services.dataprocessor.infrastructure.endpoint.amqpinternal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.dataprocessor.application.SensorDataTransformationsUpdateService;
import sharespot.services.dataprocessor.domain.SensorTypeId;

import java.io.IOException;

@Service
public class DataTransformationUpdateConsumer {

    public static final String MASTER_QUEUE = "Sharespot Data Processor Master Exchange -> Sharespot Data Processor Slave Queue";

    private final SensorDataTransformationsUpdateService updater;
    private final ObjectMapper mapper;

    public DataTransformationUpdateConsumer(SensorDataTransformationsUpdateService eraser, ObjectMapper mapper) {
        this.updater = eraser;
        this.mapper = mapper;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), SensorTypeIdImpl.class);
        updater.update(SensorTypeId.of(dto.value));
    }
}
