package pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;

import java.util.HashSet;
import java.util.Set;

public record Owners(Set<DomainId> value) {
    public static Owners empty() {
        return new Owners(new HashSet<>());
    }

    public boolean hasAny(Owners owner) {
        return value.stream().anyMatch(owner.value::contains);
    }
}
