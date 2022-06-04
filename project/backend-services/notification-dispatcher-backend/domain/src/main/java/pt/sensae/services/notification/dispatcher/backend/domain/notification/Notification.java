package pt.sensae.services.notification.dispatcher.backend.domain.notification;

import pt.sensae.services.notification.dispatcher.backend.domain.contentType.ContentType;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

public record Notification(UUID id, ContentType type, String description, Instant reportedAt,
                           NotificationContext context) {
    public static Notification of(UUID id, ContentType type, String description, Instant reportedAt,
                                  NotificationContext context) {
        return new Notification(id, type, description, reportedAt,
                context);
    }

    public String asContent() {
        return "Category: " + toTitleCase(type.category()) +
                "\nSub Category: " + toTitleCase(type.subCategory()) +
                "\nSeverity: " + toTitleCase(type.level().name().toLowerCase(Locale.ROOT)) +
                "\nDescription: " + description;
    }

    private String toTitleCase(String value) {
        String result = value.replaceAll("([A-Z])", " $1");
        return result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();
    }
}
