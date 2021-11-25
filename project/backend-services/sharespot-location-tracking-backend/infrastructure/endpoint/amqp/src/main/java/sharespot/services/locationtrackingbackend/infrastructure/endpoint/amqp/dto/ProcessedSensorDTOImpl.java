package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto;

import java.util.UUID;

public class ProcessedSensorDTOImpl {

    public String name;

    public UUID id;

    public ProcessedSensorDTOImpl(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public ProcessedSensorDTOImpl() {
    }
}
