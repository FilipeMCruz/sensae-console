package pt.sensae.services.notification.management.backend.application.notification.mapper;

import pt.sensae.services.notification.management.backend.application.notification.model.NotificationHistoryQueryCommand;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationHistoryQueryCommandDTO;

public interface NotificationHistoryQueryCommandMapper {

    NotificationHistoryQueryCommand toDomain(NotificationHistoryQueryCommandDTO dto);
}
