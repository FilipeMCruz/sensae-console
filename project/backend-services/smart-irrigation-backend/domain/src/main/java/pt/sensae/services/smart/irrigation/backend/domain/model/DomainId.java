package pt.sensae.services.smart.irrigation.backend.domain.model;

import java.util.UUID;

public record DomainId(UUID value) {
    public static DomainId of(UUID uuid) {
        return new DomainId(uuid);
    }
}
