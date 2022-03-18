package sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.datadecoder.application.DataDecodersUpdateService;
import sharespot.services.datadecoder.domain.SensorTypeId;

@Service
public class DataDecoderUpdateConsumer {

    public static final String MASTER_QUEUE = "Sharespot Data Decoder Master Exchange -> Sharespot Data Decoder Slave Queue";

    private final DataDecodersUpdateService updater;

    public DataDecoderUpdateConsumer(DataDecodersUpdateService eraser) {
        this.updater = eraser;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveEvent(SensorTypeIdImpl in) {
        updater.update(SensorTypeId.of(in.value));
    }
}
