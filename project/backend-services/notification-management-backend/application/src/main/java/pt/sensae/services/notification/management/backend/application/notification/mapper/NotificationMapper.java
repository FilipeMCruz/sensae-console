package pt.sensae.services.notification.management.backend.application.notification.mapper;

import pt.sensae.services.notification.management.backend.application.notification.model.NotificationDTO;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;

public interface NotificationMapper {

    NotificationDTO toDto(Notification notification);
}
