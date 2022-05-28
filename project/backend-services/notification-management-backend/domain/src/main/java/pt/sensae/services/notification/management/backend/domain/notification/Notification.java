package pt.sensae.services.notification.management.backend.domain.notification;

import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;

import java.time.Instant;
import java.util.UUID;

public record Notification(UUID id, ContentType type, String description, Instant reportedAt,
                           NotificationContext context) {
    public static Notification of(UUID id, ContentType type, String description, Instant reportedAt,
                                  NotificationContext context) {
        return new Notification(id, type, description, reportedAt,
                context);
    }
}
