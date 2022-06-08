package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.application.DataTransformationNotifierService;
import pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;

@Service
public class DataProcessorNotificationConsumer {

    public static final String MASTER_QUEUE = "internal.data.processor.master.queue";

    private final DataTransformationNotifierService informer;

    public DataProcessorNotificationConsumer(DataTransformationNotifierService informer) {
        this.informer = informer;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(SensorTypeIdDTOImpl dto) {
        informer.notify(dto);
    }
}
