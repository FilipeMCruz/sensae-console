package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.model;

import java.util.UUID;

public class ProcessedSensorDTOImpl {

    public String name;

    public UUID id;

    public ProcessedSensorDTOImpl(String name, String id) {
        this.name = name;
        this.id = UUID.fromString(id);
    }

    public ProcessedSensorDTOImpl() {
    }
}
