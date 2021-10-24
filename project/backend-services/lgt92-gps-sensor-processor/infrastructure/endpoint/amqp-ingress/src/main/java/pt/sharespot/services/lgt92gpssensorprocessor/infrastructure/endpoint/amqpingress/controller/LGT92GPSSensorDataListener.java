package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sharespot.services.lgt92gpssensorprocessor.application.SensorDataHandlerService;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.sensor.lgt92sensor.LGT92SensorData;

@Component
public class LGT92GPSSensorDataListener {

    private final SensorDataHandlerService handler;

    public LGT92GPSSensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = "LGT92 GPS Data Queue")
    public void receiveUpdate(LGT92SensorData in) {
        handler.publish(in);
    }
}
