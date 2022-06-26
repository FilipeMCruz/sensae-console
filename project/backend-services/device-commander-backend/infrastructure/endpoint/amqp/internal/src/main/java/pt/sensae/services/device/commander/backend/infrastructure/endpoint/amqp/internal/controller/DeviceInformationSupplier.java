package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import pt.sensae.services.device.commander.backend.application.DeviceCommandNotificationPublisherService;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.mapper.DeviceMapper;
import pt.sharespot.iot.core.IoTCoreTopic;

import javax.annotation.PostConstruct;

@Component
public class DeviceInformationSupplier {

    private final AmqpTemplate template;
    private final DeviceCommandNotificationPublisherService service;
    private final DeviceMapper deviceMapper;
    private final ObjectMapper mapper;

    public DeviceInformationSupplier(AmqpTemplate template,
                                     DeviceCommandNotificationPublisherService service,
                                     DeviceMapper deviceMapper,
                                     ObjectMapper mapper) {

        this.template = template;
        this.service = service;
        this.deviceMapper = deviceMapper;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init() {
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
