package pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderNotifierService;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;

@Service
public class DataDecoderNotificationConsumer {

    public static final String MASTER_QUEUE = "internal.data.decoder.master.queue";

    private final DataDecoderNotifierService informer;

    public DataDecoderNotificationConsumer(DataDecoderNotifierService informer) {
        this.informer = informer;
    }

    @RabbitListener(queues = MASTER_QUEUE)
    public void receiveIndexEvent(SensorTypeIdDTOImpl dto) {
        informer.notify(dto);
    }
}
