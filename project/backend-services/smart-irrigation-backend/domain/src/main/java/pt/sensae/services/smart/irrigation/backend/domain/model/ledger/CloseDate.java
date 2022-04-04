package pt.sensae.services.smart.irrigation.backend.domain.model.ledger;

import java.time.Instant;

public record CloseDate(Instant value) {
    public static CloseDate now() {
        return new CloseDate(Instant.now());
    }
}
