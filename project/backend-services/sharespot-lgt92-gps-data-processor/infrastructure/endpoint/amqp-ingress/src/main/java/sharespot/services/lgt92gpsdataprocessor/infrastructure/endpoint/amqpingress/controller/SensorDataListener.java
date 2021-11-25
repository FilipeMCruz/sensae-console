package sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpingress.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.lgt92gpsdataprocessor.application.SensorDataHandlerService;

@Service
public class SensorDataListener {

    public static final String INGRESS_QUEUE = "Sharespot LGT92 GPS Data Gateway Exchange -> Sharespot LGT92 GPS Data Processor Queue";

    private final SensorDataHandlerService handler;

    public SensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(ObjectNode in) {
        handler.publish(in);
    }
}
