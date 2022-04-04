package pt.sensae.services.smart.irrigation.backend.domain.model.ledger;

import java.time.Instant;

public record OpenDate(Instant value) {
    public static OpenDate now() {
        return new OpenDate(Instant.now());
    }
}
