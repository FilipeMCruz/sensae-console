package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Ownership(Set<DomainId> value) {

    public static Ownership of(Stream<DomainId> value) {
        return new Ownership(value.collect(Collectors.toSet()));
    }

    public Ownership and(Stream<DomainId> value) {
        this.value.addAll(value.collect(Collectors.toSet()));
        return this;
    }
}
