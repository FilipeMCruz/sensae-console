package pt.sensae.services.identity.management.backend.domain.identity.domain;

import java.util.List;

public record DomainPath(List<DomainId> path) {

    public DomainId getParent() {
        return path.get(path.size() - 2);
    }

    public static DomainPath of(List<DomainId> value) {
        return new DomainPath(value);
    }
}
