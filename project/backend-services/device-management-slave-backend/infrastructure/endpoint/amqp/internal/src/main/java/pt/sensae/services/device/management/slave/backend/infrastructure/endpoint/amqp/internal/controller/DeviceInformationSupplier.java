package pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import pt.sensae.services.device.management.slave.backend.application.SensorDataNotificationPublisherService;
import pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.mapper.DeviceMapper;
import pt.sharespot.iot.core.IoTCoreTopic;

@Component
public class DeviceInformationSupplier {

    public DeviceInformationSupplier(AmqpTemplate template, SensorDataNotificationPublisherService service, DeviceMapper mapper) {
        service.getPublisher().subscribe(outData ->
                template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, outData.keys()
                        .toString(), mapper.domainToDto(outData.device())));
    }
}
