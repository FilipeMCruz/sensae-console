package sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.IoTCoreTopic;
import sharespot.services.datadecoder.application.SensorDataNotificationPublisherService;
import sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.mapper.DataDecoderEventMapperImpl;

@Service
public class DataDecoderSupplier {

    public DataDecoderSupplier(AmqpTemplate template, SensorDataNotificationPublisherService service, DataDecoderEventMapperImpl deviceMapper, ObjectMapper mapper) {
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
