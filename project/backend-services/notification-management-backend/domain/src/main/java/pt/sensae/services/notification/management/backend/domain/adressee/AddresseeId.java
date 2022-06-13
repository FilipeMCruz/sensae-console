package pt.sensae.services.notification.management.backend.domain.adressee;

import java.util.UUID;

public record AddresseeId(UUID value) {
    public static AddresseeId of(UUID value) {
        return new AddresseeId(value);
    }
}
