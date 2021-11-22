package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.application.SensorDataHandlerService;

@Service
public class SensorDataListener {

    private final SensorDataHandlerService handler;

    public SensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = "GPS Data Queue")
    public void receiveUpdate(ProcessedSensorDataDTO in) {
        handler.publish(in);
    }
}
