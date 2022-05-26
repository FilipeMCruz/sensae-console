package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Ownership(Set<DomainId> value, boolean isSystem) {

    public static Ownership of(Stream<DomainId> value) {
        return new Ownership(value.collect(Collectors.toSet()), false);
    }

    public static Ownership system() {
        return new Ownership(new HashSet<>(), true);
    }

    public boolean isSystem() {
        return isSystem;
    }
}
