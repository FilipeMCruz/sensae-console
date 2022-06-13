package pt.sensae.services.notification.management.backend.domain;

import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import pt.sensae.services.notification.management.backend.domain.tenant.Tenant;

import java.util.Set;

public record FullNotification(Set<Tenant> recipients, Notification notification) {

    public static FullNotification of(Set<Tenant> tenant, Notification notification) {
        return new FullNotification(tenant, notification);
    }
}
