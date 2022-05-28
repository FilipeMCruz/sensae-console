package pt.sensae.services.notification.management.backend.domain.contentType;

import pt.sensae.services.notification.management.backend.domain.notification.NotificationLevel;

public record ContentType(ContentTypeId id, String category, String subCategory, NotificationLevel level) {
    public static ContentType of(ContentTypeId id, String category, String subCategory, NotificationLevel level) {
        return new ContentType(id, category, subCategory, level);
    }
}
