package sharespot.services.fastdatastore.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.fastdatastore.application.SensorDataHandlerService;
import sharespot.services.fastdatastore.infrastructure.endpoint.amqp.model.ProcessedSensorDataDTOImpl;

@Service
public class SensorDataListener {

    public static final String QUEUE = "Sharespot GPS Data Processor Exchange -> Sharespot Fast Data Store Queue";

    private final SensorDataHandlerService handler;

    public SensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(ProcessedSensorDataDTOImpl in) {
        try {
            handler.publish(in);
        } catch (Exception ignore) {
        }
    }
}
