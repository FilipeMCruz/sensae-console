package pt.sensae.services.notification.management.backend.domain.contentType;

import java.util.UUID;

public record ContentTypeId(UUID value) {
    public static ContentTypeId of(UUID value) {
        return new ContentTypeId(value);
    }

    public static ContentTypeId create() {
        return new ContentTypeId(UUID.randomUUID());
    }
}
