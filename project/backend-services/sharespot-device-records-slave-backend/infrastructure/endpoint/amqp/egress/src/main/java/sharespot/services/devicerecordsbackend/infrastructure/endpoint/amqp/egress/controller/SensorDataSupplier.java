package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.buf.mapper.MessageMapper;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.application.SensorDataHandlerService;

@Component
public class SensorDataSupplier {

    Logger logger = LoggerFactory.getLogger(SensorDataSupplier.class);

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    public SensorDataSupplier(AmqpTemplate template, SensorDataHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> {
                    logSuppliedMessage(outData);
                    template.send(TOPIC_EXCHANGE,
                            outData.routingKeys.toString(),
                            new Message(MessageMapper.toBuf(outData).toByteArray()));
                });
    }

    private void logSuppliedMessage(MessageSupplied<ProcessedSensorDataDTO> in) {
        logger.info("Data Id Supplied: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
