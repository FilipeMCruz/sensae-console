package pt.sensae.services.notification.management.backend.application.notification.service;

import pt.sensae.services.notification.management.backend.domain.FullNotification;

public interface SMSNotificationService {
    void send(FullNotification notification);
}
