package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.notification.mapper.NotificationMapper;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationDTO;
import pt.sensae.services.notification.management.backend.application.notification.model.ReadNotificationDTO;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationId;
import pt.sensae.services.notification.management.backend.domain.tenant.Tenant;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.ContentTypeDTOImpl;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.NotificationDTOImpl;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.NotificationReaderDTOImpl;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.ReadNotificationDTOImpl;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationMapperImpl implements NotificationMapper {
    @Override
    public NotificationDTO toDto(Notification notification, Set<Tenant> readers) {
        var dto = new NotificationDTOImpl();
        dto.id = notification.id().value();
        dto.description = notification.description();
        dto.reportedAt = String.valueOf(notification.reportedAt().toEpochMilli());

        dto.contentType = new ContentTypeDTOImpl();
        dto.contentType.category = notification.type().category();
        dto.contentType.subCategory = notification.type().subCategory();
        dto.contentType.level = NotificationLevelMapper.extract(notification.type().level());
        dto.readers = readers.stream().map(t -> {
            var notificationReaderDTO = new NotificationReaderDTOImpl();
            notificationReaderDTO.oid = t.id().value().toString();
            notificationReaderDTO.name = t.name().value();
            return notificationReaderDTO;
        }).collect(Collectors.toSet());
        return dto;
    }

    @Override
    public NotificationId toModel(ReadNotificationDTO notificationDTO) {
        var dto = (ReadNotificationDTOImpl) notificationDTO;
        return NotificationId.of(dto.id);
    }
}
