package pt.sensae.services.notification.management.backend.domain;

import java.util.HashSet;
import java.util.Set;

public record Domains(Set<DomainId> value) {
    public static Domains of(Set<DomainId> value) {
        return new Domains(value);
    }

    public static Domains empty() {
        return new Domains(new HashSet<>());
    }

    public static Domains single(DomainId id) {
        var ids = new HashSet<DomainId>();
        ids.add(id);
        return new Domains(ids);
    }
}
