package sharespot.services.datadecodermaster.infrastructure.endpoint.amqp.egress.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sharespot.services.datadecodermaster.application.DataDecoderHandlerService;

@Component
public class SensorDataEmitter {

    public static final String EXCHANGE = "Sharespot Data Decoder Master Exchange";

    public SensorDataEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, DataDecoderHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend(EXCHANGE, "", outData));
    }
}
