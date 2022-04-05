package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import java.time.Instant;

public record CloseDate(Instant value) {
    public static CloseDate now() {
        return new CloseDate(Instant.now());
    }

    public static CloseDate empty() {
        return new CloseDate(null);
    }
}
