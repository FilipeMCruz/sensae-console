package pt.sensae.services.notification.management.backend.application;

import pt.sensae.services.notification.management.backend.domain.FullNotification;

public interface EmailNotificationService {
    void send(FullNotification notification);
}
