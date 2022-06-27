package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.application.DataTransformationNotifierService;
import pt.sensae.services.data.processor.master.backend.application.TransformationLastTimeSeenRegisterService;
import pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;

@Service
public class DataProcessorRequestConsumer {

    private final DataTransformationNotifierService informer;

    private final TransformationLastTimeSeenRegisterService lastTimeSeenRegistry;

    public DataProcessorRequestConsumer(DataTransformationNotifierService informer,
                                        TransformationLastTimeSeenRegisterService lastTimeSeenRegistry) {
        this.informer = informer;
        this.lastTimeSeenRegistry = lastTimeSeenRegistry;
    }

    @RabbitListener(queues = "#{queueNamingService.getTransformationRequestQueueName()}")
    public void receiveIndexEvent(SensorTypeIdDTOImpl dto) {
        informer.notify(dto);
        lastTimeSeenRegistry.updateLastTimeSeen(dto);
    }
}
