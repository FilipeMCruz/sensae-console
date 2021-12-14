package sharespot.services.datastore.infrastructure.endpoint.amqp.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.datastore.application.SensorDataHandlerService;
import sharespot.services.datastore.application.model.MessageConsumed;

@Service
public class SensorDataListener {

    public static final String QUEUE = "Sharespot Data Store Queue";

    private final SensorDataHandlerService handler;

    public SensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(MessageConsumed<ObjectNode> in) {
        handler.publish(in);
    }
}
