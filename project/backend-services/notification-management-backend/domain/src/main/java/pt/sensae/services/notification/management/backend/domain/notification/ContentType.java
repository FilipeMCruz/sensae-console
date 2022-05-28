package pt.sensae.services.notification.management.backend.domain.notification;

public record ContentType(String category, String subCategory, NotificationLevel level) {
    public static ContentType of(String category, String subCategory, NotificationLevel level) {
        return new ContentType(category, subCategory, level);
    }
}
