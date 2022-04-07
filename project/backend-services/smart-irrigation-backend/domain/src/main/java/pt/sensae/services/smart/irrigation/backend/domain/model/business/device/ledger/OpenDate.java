package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import java.time.Instant;

public record OpenDate(Instant value) {

    public boolean isBefore(CloseDate close) {
        return close.value() != null && value.isAfter(close.value());
    }

    public CloseDate toClose() {
        return new CloseDate(value.minusMillis(50));
    }

    public static OpenDate now() {
        return new OpenDate(Instant.now());
    }

    public static OpenDate of(long millis) {
        return new OpenDate(Instant.ofEpochMilli(millis));
    }
}
