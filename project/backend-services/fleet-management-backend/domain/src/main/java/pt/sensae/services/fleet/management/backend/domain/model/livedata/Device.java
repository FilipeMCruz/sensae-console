package pt.sensae.services.fleet.management.backend.domain.model.livedata;

import java.util.Set;
import java.util.UUID;

public record Device(String name, UUID id, Set<RecordEntry> records) {
}
