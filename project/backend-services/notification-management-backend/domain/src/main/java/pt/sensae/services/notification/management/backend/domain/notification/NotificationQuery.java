package pt.sensae.services.notification.management.backend.domain.notification;

import pt.sensae.services.notification.management.backend.domain.Domains;

import java.time.Instant;

public record NotificationQuery(Instant start, Instant end, Domains domains) {
}
