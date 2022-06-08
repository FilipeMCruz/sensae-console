package pt.sensae.services.data.store.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

@Service
public class SensorDataHandlerService {

    private final DataRepository repository;

    public SensorDataHandlerService(DataRepository repository) {
        this.repository = repository;
    }

    public void publish(MessageConsumed<ObjectNode, SensorRoutingKeys> in) {
        repository.insert(in.data);
    }
}
