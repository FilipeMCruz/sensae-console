package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataWithRecordDTO;
import sharespot.services.devicerecordsbackend.application.SensorDataHandlerService;
import sharespot.services.devicerecordsbackend.domain.model.message.MessageSupplied;

@Component
public class SensorDataEmitter {

    Logger logger = LoggerFactory.getLogger(SensorDataEmitter.class);

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    public SensorDataEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, SensorDataHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> {
                    logSuppliedMessage(outData);
                    template.convertAndSend(TOPIC_EXCHANGE, outData.routingKeys.toString(), outData);
                });
    }

    private void logSuppliedMessage(MessageSupplied<ProcessedSensorDataWithRecordDTO> in) {
        logger.info("Data Id Supplied: {}", in.data.dataId());
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
