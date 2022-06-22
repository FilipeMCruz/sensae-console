package pt.sensae.services.data.decoder.slave.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sensae.services.data.decoder.slave.backend.application.DataDecoderEventMapper;
import pt.sensae.services.data.decoder.slave.backend.application.SensorDataNotificationPublisherService;

import javax.annotation.PostConstruct;

@Service
public class DataDecoderSupplier {

    private final AmqpTemplate template;
    private final SensorDataNotificationPublisherService service;
    private final DataDecoderEventMapper deviceMapper;
    private final ObjectMapper mapper;

    public DataDecoderSupplier(AmqpTemplate template,
                               SensorDataNotificationPublisherService service,
                               DataDecoderEventMapper deviceMapper,
                               ObjectMapper mapper) {
        this.template = template;
        this.service = service;
        this.deviceMapper = deviceMapper;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init() {
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
