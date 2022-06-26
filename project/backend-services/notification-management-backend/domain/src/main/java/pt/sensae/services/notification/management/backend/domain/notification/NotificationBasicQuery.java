package pt.sensae.services.notification.management.backend.domain.notification;

import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;

import java.util.List;

public record NotificationBasicQuery(Domains domains, List<ContentType> configs, Integer limit) {
    public static NotificationBasicQuery of(Domains domains, List<ContentType> configs) {
        return new NotificationBasicQuery(domains, configs, 100);
    }
}
