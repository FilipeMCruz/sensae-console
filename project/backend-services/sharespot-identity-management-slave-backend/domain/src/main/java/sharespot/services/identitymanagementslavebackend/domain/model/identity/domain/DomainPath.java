package sharespot.services.identitymanagementslavebackend.domain.model.identity.domain;

import java.util.List;

public record DomainPath(List<DomainId> path) {

    public static DomainPath of(List<DomainId> value) {
        return new DomainPath(value);
    }
}
