package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sharespot.services.devicerecordsbackend.application.SensorDataHandlerService;

@Component
public class SensorDataEmitter {

    Logger logger = LoggerFactory.getLogger(SensorDataEmitter.class);
    
    public SensorDataEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, SensorDataHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> {
                    logger.info("sending: " + outData.toString());
                    template.convertAndSend("GPS Data With Records Exchange", "", outData);
                });
    }
}
