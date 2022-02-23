package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sharespot.services.devicerecordsbackend.application.RecordEventHandlerService;

@Component
public class SensorDataEmitter {

    public static final String EXCHANGE = "Sharespot Device Records Master Exchange";

    public SensorDataEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, RecordEventHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend(EXCHANGE, "", outData));
    }
}
