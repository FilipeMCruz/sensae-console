package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device;

import java.util.Set;
import java.util.UUID;

public record Device(String name, DeviceType type, UUID id, Set<RecordEntry> records) {
}
