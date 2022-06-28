package pt.sensae.services.notification.management.backend.domain.notification;

import java.util.UUID;

public record NotificationId(UUID value) {
    public static NotificationId of(UUID value) {
        return new NotificationId(value);
    }
}
