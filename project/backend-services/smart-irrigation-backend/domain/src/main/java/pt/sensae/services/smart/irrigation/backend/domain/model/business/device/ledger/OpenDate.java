package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;

import java.time.Instant;

public record OpenDate(Instant value) {

    public void isBefore(CloseDate close) {
        if (close.value() != null && value.isAfter(close.value()))
            throw new NotValidException("Open Time can't happen after close time");
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
