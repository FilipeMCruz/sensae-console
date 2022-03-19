package sharespot.services.dataprocessormaster.infrastructure.endpoint.amqp.egress.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sharespot.services.dataprocessormaster.application.DataTransformationHandlerService;

@Component
public class SensorDataEmitter {

    public static final String EXCHANGE = "Sharespot Data Processor Master Exchange";

    public SensorDataEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, DataTransformationHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend(EXCHANGE, "", outData));
    }
}
