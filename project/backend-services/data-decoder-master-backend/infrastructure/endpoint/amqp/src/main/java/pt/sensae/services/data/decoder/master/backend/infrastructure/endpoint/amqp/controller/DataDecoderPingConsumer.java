package pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.application.DecoderLastTimeSeenRegisterService;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;

@Service
public class DataDecoderPingConsumer {

    private final DecoderLastTimeSeenRegisterService lastTimeSeenRegistry;

    public DataDecoderPingConsumer(DecoderLastTimeSeenRegisterService lastTimeSeenRegistry) {
        this.lastTimeSeenRegistry = lastTimeSeenRegistry;
    }

    @RabbitListener(queues = "#{queueNamingService.getDecoderPingQueueName()}")
    public void receiveIndexEvent(SensorTypeIdDTOImpl dto) {
        lastTimeSeenRegistry.updateLastTimeSeen(dto);
    }
}
