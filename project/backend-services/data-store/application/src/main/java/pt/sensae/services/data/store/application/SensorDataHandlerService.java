package pt.sensae.services.data.store.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

@Service
public class SensorDataHandlerService {

    private final DataRepository repository;

    public SensorDataHandlerService(DataRepository repository) {
        this.repository = repository;
    }

    public void publish(MessageConsumed<DataUnitDTO, DataRoutingKeys> in) {
        repository.insert("unrouted", in);
    }

    public void publishUnprocessed(MessageConsumed<ObjectNode, DataRoutingKeys> in) {
        repository.insert(in);
    }
}
