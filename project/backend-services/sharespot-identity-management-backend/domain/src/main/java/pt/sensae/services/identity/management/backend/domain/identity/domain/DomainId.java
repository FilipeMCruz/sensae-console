package pt.sensae.services.identity.management.backend.domain.identity.domain;

import java.util.UUID;

public record DomainId(UUID value) {

    public static DomainId of(UUID value) {
        return new DomainId(value);
    }
}
