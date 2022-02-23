package sharespot.services.devicerecordsbackend.domain.model.permissions;

import java.util.Set;

public record Permissions(Set<DomainId> read, Set<DomainId> readAndWrite) {
}
