package pt.sensae.services.notification.management.backend.application.notification.mapper;

import pt.sensae.services.notification.management.backend.application.notification.model.NotificationDTO;
import pt.sensae.services.notification.management.backend.application.notification.model.ReadNotificationDTO;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationId;
import pt.sensae.services.notification.management.backend.domain.tenant.Tenant;

import java.util.Set;

public interface NotificationMapper {

    NotificationDTO toDto(Notification notification, Set<Tenant> readers);

    NotificationId toModel(ReadNotificationDTO notificationDTO);
}
