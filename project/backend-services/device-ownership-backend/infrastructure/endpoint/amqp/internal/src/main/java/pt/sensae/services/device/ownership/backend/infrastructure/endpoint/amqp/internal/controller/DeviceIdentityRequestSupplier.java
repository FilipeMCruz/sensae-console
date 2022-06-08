package pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sensae.services.device.ownership.backend.application.SensorDataNotificationPublisherService;
import pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.internal.mapper.DeviceNotificationEventMapperImpl;

@Service
public class DeviceIdentityRequestSupplier {

    public DeviceIdentityRequestSupplier(AmqpTemplate template,
                                         SensorDataNotificationPublisherService service,
                                         DeviceNotificationEventMapperImpl deviceMapper,
                                         ObjectMapper mapper) {
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
