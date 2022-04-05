package pt.sensae.services.smart.irrigation.backend.domain.model.data;

import java.time.Instant;

public record ReportTime(Instant value) {

    public static ReportTime of(long millis) {
        return new ReportTime(Instant.ofEpochMilli(millis));
    }
}
