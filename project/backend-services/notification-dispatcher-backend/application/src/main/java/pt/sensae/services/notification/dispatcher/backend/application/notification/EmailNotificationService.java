package pt.sensae.services.notification.dispatcher.backend.application.notification;

import pt.sensae.services.notification.dispatcher.backend.domain.FullNotification;

public interface EmailNotificationService {
    void send(FullNotification notification);
}
