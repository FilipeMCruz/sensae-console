package pt.sensae.services.notification.dispatcher.backend.domain.contentType;

import pt.sensae.services.notification.dispatcher.backend.domain.notification.NotificationLevel;

public record ContentType(String category, String subCategory, NotificationLevel level) {
    public static ContentType of(String category, String subCategory, NotificationLevel level) {
        return new ContentType(category, subCategory, level);
    }
}
