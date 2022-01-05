package sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.model.livedata;

import java.util.Set;
import java.util.UUID;

public record Device(String name, UUID id, Set<RecordEntry> records) {
}
