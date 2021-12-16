package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.devicerecordsbackend.application.SensorDataHandlerService;

@Component
public class SensorDataSupplier {

    Logger logger = LoggerFactory.getLogger(SensorDataSupplier.class);

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    public SensorDataSupplier(@Qualifier("amqpTemplate") AmqpTemplate template, SensorDataHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> {
                    logSuppliedMessage(outData);
                    template.convertAndSend(TOPIC_EXCHANGE, outData.routingKeys.toString(), outData);
                });
    }

    private void logSuppliedMessage(MessageSupplied<ProcessedSensorDataWithRecordsDTO> in) {
        logger.info("Data Id Supplied: {}", in.data.dataId());
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
