package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model;

import java.util.Set;
import java.util.UUID;

public record Device(String name, UUID id, Set<RecordEntry> records) {
}
