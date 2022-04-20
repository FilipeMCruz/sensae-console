package sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.datadecoder.application.DataDecodersUpdateService;
import sharespot.services.datadecoder.domain.SensorTypeId;

import java.io.IOException;

@Service
public class DataDecoderUpdateConsumer {

    public static final String MASTER_QUEUE = "Sharespot Data Decoder Master Exchange -> Sharespot Data Decoder Slave Queue";

    private final DataDecodersUpdateService updater;
    private final ObjectMapper mapper;

    public DataDecoderUpdateConsumer(DataDecodersUpdateService eraser, ObjectMapper mapper) {
        this.updater = eraser;
        this.mapper = mapper;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), SensorTypeIdImpl.class);
        updater.update(SensorTypeId.of(dto.value));
    }
}
