package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import pt.sensae.services.device.commander.backend.application.DeviceCommandNotificationPublisherService;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.mapper.DeviceMapper;
import pt.sharespot.iot.core.IoTCoreTopic;

@Component
public class DeviceInformationSupplier {

    public DeviceInformationSupplier(AmqpTemplate template, DeviceCommandNotificationPublisherService service, DeviceMapper deviceMapper, ObjectMapper mapper) {
        service.getPublisher().subscribe(outData -> {
            var deviceDTO = deviceMapper.domainToDto(outData.device());
            try {
                template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, outData.keys()
                        .toString(), mapper.writeValueAsBytes(deviceDTO));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
