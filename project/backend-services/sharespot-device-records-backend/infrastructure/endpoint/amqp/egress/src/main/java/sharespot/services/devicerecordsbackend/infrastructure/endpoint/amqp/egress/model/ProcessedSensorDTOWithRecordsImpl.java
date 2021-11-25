package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model;

import java.util.UUID;

public class ProcessedSensorDTOWithRecordsImpl {

    public String name;

    public UUID id;

    public ProcessedSensorDTOWithRecordsImpl(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public ProcessedSensorDTOWithRecordsImpl() {
    }
}
