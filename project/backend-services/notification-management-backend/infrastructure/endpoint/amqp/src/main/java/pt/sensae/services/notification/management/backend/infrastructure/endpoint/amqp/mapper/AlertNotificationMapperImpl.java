package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.mapper;

import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationContext;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationLevel;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.model.AlertLevelDTO;

import java.time.Instant;
import java.util.stream.Collectors;

public class AlertNotificationMapperImpl {

    public static Notification dtoToDomain(AlertDTO dto) {
        var collect = dto.context.domainIds.stream().map(DomainId::of).collect(Collectors.toSet());
        var context = new NotificationContext(dto.context.dataIds, dto.context.deviceIds, Domains.of(collect), dto.context.other);
        var contentType = new ContentType(dto.category, "subcat", extract(dto.level));//TODO
        return new Notification(dto.id, contentType, dto.description, Instant.ofEpochMilli(dto.reportedAt), context);
    }

    private static NotificationLevel extract(AlertLevelDTO dto) {
        return switch (dto) {
            case INFORMATION -> NotificationLevel.INFORMATION;
//            case WATCH -> NotificationLevel.WATCH; //TODO
//            case ADVISORY -> NotificationLevel.ADVISORY;
            case WARNING -> NotificationLevel.WARNING;
            case CRITICAL -> NotificationLevel.CRITICAL;
        };
    }
}
