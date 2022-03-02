package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sharespot.services.identitymanagementbackend.application.service.device.DeviceUpdateHandlerService;

@Component
public class DeviceUpdateEmitter {

    public static final String EXCHANGE = "Sharespot Identity Management Master Exchange";

    public DeviceUpdateEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, DeviceUpdateHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend(EXCHANGE, "", outData));
    }
}
