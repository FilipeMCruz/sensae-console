package pt.sensae.services.notification.management.backend.domain.adressee;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record Domains(Set<UUID> value) {
    public static Domains of(Set<UUID> value) {
        return new Domains(value);
    }

    public static Domains empty() {
        return new Domains(new HashSet<>());
    }
}
