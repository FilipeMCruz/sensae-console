package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sharespot.services.lgt92gpssensorprocessor.application.SensorDataHandlerService;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.sensor.lgt92sensor.LGT92SensorData;

@Component
public class LGT92GPSSensorDataListener {

    Logger logger = LoggerFactory.getLogger(LGT92GPSSensorDataListener.class);

    private final SensorDataHandlerService handler;

    public LGT92GPSSensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = "LGT92 GPS Data Queue")
    public void receiveUpdate(LGT92SensorData in) {
        logger.info("New Data: " + in.uuid);
        handler.publish(in);
    }
}
