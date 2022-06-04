package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.notification.mapper.NotificationMapper;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationDTO;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.ContentTypeDTOImpl;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.NotificationDTOImpl;

@Service
public class NotificationMapperImpl implements NotificationMapper {
    @Override
    public NotificationDTO toDto(Notification notification) {
        var dto = new NotificationDTOImpl();
        dto.id = notification.id();
        dto.description = notification.description();
        dto.reportedAt = String.valueOf(notification.reportedAt().toEpochMilli());

        dto.contentType = new ContentTypeDTOImpl();
        dto.contentType.category = notification.type().category();
        dto.contentType.subCategory = notification.type().subCategory();
        dto.contentType.level = NotificationLevelMapper.extract(notification.type().level());

        return dto;
    }
}
