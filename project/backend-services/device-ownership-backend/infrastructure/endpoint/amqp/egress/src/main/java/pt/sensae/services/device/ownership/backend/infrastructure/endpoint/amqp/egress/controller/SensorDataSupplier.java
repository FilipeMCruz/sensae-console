package pt.sensae.services.device.ownership.backend.infrastructure.endpoint.amqp.egress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.sensor.mapper.MessageMapper;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import pt.sensae.services.device.ownership.backend.application.SensorDataPublisherService;

import javax.annotation.PostConstruct;

@Component
public class SensorDataSupplier {

    public final Logger logger = LoggerFactory.getLogger(SensorDataSupplier.class);
    private final AmqpTemplate template;
    private final SensorDataPublisherService service;

    public SensorDataSupplier(AmqpTemplate template, SensorDataPublisherService service) {
        this.template = template;
        this.service = service;
    }

    @PostConstruct
    private void init() {
        service.getSinglePublisher().subscribe(outData -> {
            logSuppliedMessage(outData);
            template.send(IoTCoreTopic.DATA_EXCHANGE,
                    outData.routingKeys.toString(),
                    new Message(MessageMapper.toBuf(outData).toByteArray()));
        });
    }

    private void logSuppliedMessage(MessageSupplied<SensorDataDTO, SensorRoutingKeys> in) {
        logger.info("Data Id Supplied: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
