package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.SensorDataHandlerService;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.model.ProcessedSensorDataDTOImpl;

@Service
public class SensorDataListener {

    public static final String INGRESS_QUEUE = "Sharespot LGT92 GPS Data Processor Exchange -> Sharespot Device Records Queue";

    private final SensorDataHandlerService handler;

    public SensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(ProcessedSensorDataDTOImpl in) {
        handler.publish(in);
    }
}
