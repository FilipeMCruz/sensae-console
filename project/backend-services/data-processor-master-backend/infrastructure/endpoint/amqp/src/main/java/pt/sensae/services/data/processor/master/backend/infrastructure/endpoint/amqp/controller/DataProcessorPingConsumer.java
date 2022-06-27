package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.application.DataTransformationNotifierService;
import pt.sensae.services.data.processor.master.backend.application.TransformationLastTimeSeenRegisterService;
import pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;

@Service
public class DataProcessorPingConsumer {

    private final TransformationLastTimeSeenRegisterService lastTimeSeenRegistry;


    public DataProcessorPingConsumer(TransformationLastTimeSeenRegisterService lastTimeSeenRegistry) {
        this.lastTimeSeenRegistry = lastTimeSeenRegistry;
    }

    @RabbitListener(queues = "#{queueNamingService.getTransformationPingQueueName()}")
    public void receiveIndexEvent(SensorTypeIdDTOImpl dto) {
        lastTimeSeenRegistry.updateLastTimeSeen(dto);
    }
}
