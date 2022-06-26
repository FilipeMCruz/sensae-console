package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.notification.mapper.NotificationHistoryQueryCommandMapper;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationHistoryQueryCommand;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationHistoryQueryCommandDTO;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationHistoryQueryCommandType;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.NotificationHistoryQueryDTOImpl;

import java.time.Instant;

@Service
public class NotificationHistoryQueryCommandMapperImpl implements NotificationHistoryQueryCommandMapper {
    @Override
    public NotificationHistoryQueryCommand toDomain(NotificationHistoryQueryCommandDTO dto) {
        var in = (NotificationHistoryQueryDTOImpl) dto;
        var notificationHistoryQueryCommand = new NotificationHistoryQueryCommand();

        if (in.endTime != null && in.startTime != null) {
            notificationHistoryQueryCommand.type = NotificationHistoryQueryCommandType.TEMPORAL;
            notificationHistoryQueryCommand.end = Instant.ofEpochMilli(Long.parseLong(in.endTime));
            notificationHistoryQueryCommand.start = Instant.ofEpochMilli(Long.parseLong(in.startTime));
        } else {
            notificationHistoryQueryCommand.type = NotificationHistoryQueryCommandType.BASIC;
        }

        return notificationHistoryQueryCommand;

    }
}
