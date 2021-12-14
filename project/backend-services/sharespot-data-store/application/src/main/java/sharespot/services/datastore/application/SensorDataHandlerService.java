package sharespot.services.datastore.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import sharespot.services.datastore.application.model.MessageConsumed;

@Service
public class SensorDataHandlerService {

    private final DataRepository repository;

    public SensorDataHandlerService(DataRepository repository) {
        this.repository = repository;
    }

    public void publish(MessageConsumed<ObjectNode> in) {
        repository.insert(in.routingKeys.toString(), in.data);
    }
}
