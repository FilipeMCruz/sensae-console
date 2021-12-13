package sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sharespot.services.lgt92gpsdataprocessor.application.SensorDataHandlerService;

@Component
public class SensorDataEmitter {

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    public SensorDataEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, SensorDataHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend(TOPIC_EXCHANGE, outData.routingKeys.toString(), outData));
    }
}
