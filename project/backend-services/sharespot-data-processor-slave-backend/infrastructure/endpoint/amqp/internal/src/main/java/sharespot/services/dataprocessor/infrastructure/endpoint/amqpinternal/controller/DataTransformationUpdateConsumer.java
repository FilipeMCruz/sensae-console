package sharespot.services.dataprocessor.infrastructure.endpoint.amqpinternal.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.dataprocessor.application.SensorDataTransformationsUpdateService;
import sharespot.services.dataprocessor.domain.SensorTypeId;

@Service
public class DataTransformationUpdateConsumer {

    public static final String MASTER_QUEUE = "Sharespot Data Processor Master Exchange -> Sharespot Data Processor Slave Queue";

    private final SensorDataTransformationsUpdateService updater;

    public DataTransformationUpdateConsumer(SensorDataTransformationsUpdateService eraser) {
        this.updater = eraser;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveEvent(SensorTypeIdImpl in) {
        updater.update(SensorTypeId.of(in.value));
    }
}
