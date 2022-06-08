package pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sensae.services.data.processor.slave.backend.application.SensorDataNotificationPublisherService;
import pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.internal.mapper.DataTransformationEventMapperImpl;

@Service
public class DataTransformationSupplier {

    public DataTransformationSupplier(AmqpTemplate template, SensorDataNotificationPublisherService service, DataTransformationEventMapperImpl deviceMapper, ObjectMapper mapper) {
        service.getPublisher().subscribe(outData -> {
            var deviceDTO = deviceMapper.domainToDto(outData.type());
            try {
                template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, outData.keys()
                        .toString(), mapper.writeValueAsBytes(deviceDTO));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
