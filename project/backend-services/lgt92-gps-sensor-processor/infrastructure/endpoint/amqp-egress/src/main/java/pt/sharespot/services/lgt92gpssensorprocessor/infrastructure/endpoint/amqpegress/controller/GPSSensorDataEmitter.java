package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpegress.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sharespot.services.lgt92gpssensorprocessor.application.SensorDataHandlerService;

@Component
public class GPSSensorDataEmitter {

    public GPSSensorDataEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, SensorDataHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend("General GPS Data Exchange", "", outData));
    }
}
