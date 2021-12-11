package sharespot.services.devicerecordsbackend.domain.model.sensors;

import sharespot.services.devicerecordsbackend.domain.model.records.Device;

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

    public ProcessedSensor withDevice(Device device) {
        if (device.name().value().isBlank()) {
            return this;
        } else {
            return new ProcessedSensor(device.name().value(), this.id);
        }
    }
}
