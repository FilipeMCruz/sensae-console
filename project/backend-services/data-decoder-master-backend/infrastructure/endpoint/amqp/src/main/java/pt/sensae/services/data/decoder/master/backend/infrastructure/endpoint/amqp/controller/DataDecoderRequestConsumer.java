package pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderNotifierService;
import pt.sensae.services.data.decoder.master.backend.application.DecoderLastTimeSeenRegisterService;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;

@Service
public class DataDecoderRequestConsumer {

    private final DataDecoderNotifierService informer;

    private final DecoderLastTimeSeenRegisterService lastTimeSeenRegistry;

    public DataDecoderRequestConsumer(DataDecoderNotifierService informer, DecoderLastTimeSeenRegisterService lastTimeSeenRegistry) {
        this.informer = informer;
        this.lastTimeSeenRegistry = lastTimeSeenRegistry;
    }

    @RabbitListener(queues = "#{queueNamingService.getDecoderRequestQueueName()}")
    public void receiveIndexEvent(SensorTypeIdDTOImpl dto) {
        informer.notify(dto);
        lastTimeSeenRegistry.updateLastTimeSeen(dto);
    }
}
