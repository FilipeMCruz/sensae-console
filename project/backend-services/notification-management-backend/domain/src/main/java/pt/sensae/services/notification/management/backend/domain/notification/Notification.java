package pt.sensae.services.notification.management.backend.domain.notification;

import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public record Notification(NotificationId id, ContentType type, String description, Instant reportedAt,
                           NotificationContext context, Set<AddresseeId> readers) {
    public static Notification of(NotificationId id, ContentType type, String description, Instant reportedAt,
                                  NotificationContext context, Set<AddresseeId> readAddressees) {
        return new Notification(id, type, description, reportedAt,
                context, readAddressees);
    }

    public static Notification of(NotificationId id, ContentType type, String description, Instant reportedAt,
                                  NotificationContext context) {
        return new Notification(id, type, description, reportedAt,
                context, new HashSet<>());
    }
}
