package pt.sensae.services.data.validator.backend.infrastructure.endpoint.amqpegress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import pt.sensae.services.data.validator.backend.application.SensorDataHandlerService;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.sensor.mapper.MessageMapper;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.MessageSupplied;

@Component
public class SensorDataSupplier {

    Logger logger = LoggerFactory.getLogger(SensorDataSupplier.class);

    public SensorDataSupplier(AmqpTemplate template, SensorDataHandlerService service) {
        service.getSinglePublisher()
                .subscribe(outData -> {
                    logSuppliedMessage(outData);
                    template.send(IoTCoreTopic.DATA_EXCHANGE,
                            outData.routingKeys.toString(),
                            new Message(MessageMapper.toBuf(outData).toByteArray()));
                });
    }

    private void logSuppliedMessage(MessageSupplied<SensorDataDTO> in) {
        logger.info("Data Id Supplied: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
