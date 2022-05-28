package pt.sensae.services.notification.management.backend.domain.notification;

import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeConfig;
import pt.sensae.services.notification.management.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;

import java.time.Instant;

public record NotificationQuery(Instant start, Instant end, Domains domains) {
    public static NotificationQuery of(Instant start, Instant end, Domains domains) {
        return new NotificationQuery(start, end, domains);
    }
}
