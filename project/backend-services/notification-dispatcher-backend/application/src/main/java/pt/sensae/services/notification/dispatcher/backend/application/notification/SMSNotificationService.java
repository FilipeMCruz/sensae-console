package pt.sensae.services.notification.dispatcher.backend.application.notification;

import pt.sensae.services.notification.dispatcher.backend.domain.FullNotification;

public interface SMSNotificationService {
    void send(FullNotification notification);
}
