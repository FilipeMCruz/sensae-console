package pt.sensae.services.notification.management.backend.domain.notification;

import java.util.Set;
import java.util.UUID;

public record NotificationContext(Set<UUID> dataIds, Set<UUID> deviceIds, Set<UUID> domainIds, String other) {
    public static NotificationContext of(Set<UUID> dataIds, Set<UUID> deviceIds, Set<UUID> domainIds, String other) {
        return new NotificationContext(dataIds, deviceIds, domainIds, other);
    }
}
