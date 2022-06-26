package pt.sensae.services.notification.management.backend.domain.notification;

import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;

import java.time.Instant;
import java.util.List;

public record NotificationTemporalQuery(Instant start, Instant end, Domains domains, List<ContentType> configs,
                                        Integer limit) {
    public static NotificationTemporalQuery of(Instant start, Instant end, Domains domains, List<ContentType> configs) {
        return new NotificationTemporalQuery(start, end, domains, configs, 100);
    }
}
