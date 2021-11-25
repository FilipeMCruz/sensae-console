package sharespot.services.devicerecordsbackend.domain.model.sensors;

import java.util.UUID;

public class ProcessedSensor {

    private final String name;

    private final UUID id;

    public ProcessedSensor(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }
}
