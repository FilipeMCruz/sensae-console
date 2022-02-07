package sharespot.services.datavalidator.infrastructure.endpoint.amqpegress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.datavalidator.application.SensorDataHandlerService;

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

    private void logSuppliedMessage(MessageSupplied<ProcessedSensorDataDTO> in) {
        logger.info("Data Id Supplied: {}", in.data.dataId());
        logger.info("RoutingKeys: {}", in.routingKeys.details());
    }
}
