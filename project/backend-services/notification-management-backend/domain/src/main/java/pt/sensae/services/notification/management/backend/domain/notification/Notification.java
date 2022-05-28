package pt.sensae.services.notification.management.backend.domain.notification;

import java.util.UUID;

public record Notification(UUID id, ContentType type, String description, Long reportedAt,
                           NotificationContext context) {
    public static Notification of(UUID id, ContentType type, String description, Long reportedAt,
                                  NotificationContext context) {
        return new Notification(id, type, description, reportedAt,
                context);
    }
}
