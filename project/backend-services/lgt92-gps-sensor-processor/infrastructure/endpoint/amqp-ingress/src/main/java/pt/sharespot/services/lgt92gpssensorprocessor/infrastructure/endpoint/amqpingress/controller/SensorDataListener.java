package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.services.lgt92gpssensorprocessor.application.SensorDataHandlerService;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.SensorData;

@Service
public class SensorDataListener {

    private final SensorDataHandlerService handler;

    public SensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = "LGT92 GPS Data Queue")
    public void receiveUpdate(SensorData in) {
        handler.publish(in);
    }
}