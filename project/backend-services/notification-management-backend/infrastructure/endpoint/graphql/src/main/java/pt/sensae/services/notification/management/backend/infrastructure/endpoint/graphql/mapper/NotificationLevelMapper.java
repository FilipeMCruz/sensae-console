package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.mapper;

import pt.sensae.services.notification.management.backend.domain.notification.NotificationLevel;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.NotificationLevelDTOImpl;

public class NotificationLevelMapper {

    static NotificationLevelDTOImpl extract(NotificationLevel level) {
        return switch (level) {
            case INFORMATION -> NotificationLevelDTOImpl.INFORMATION;
            case WATCH -> NotificationLevelDTOImpl.WATCH;
            case ADVISORY -> NotificationLevelDTOImpl.ADVISORY;
            case WARNING -> NotificationLevelDTOImpl.WARNING;
            case CRITICAL -> NotificationLevelDTOImpl.CRITICAL;
        };
    }

    static NotificationLevel extract(NotificationLevelDTOImpl level) {
        return switch (level) {
            case INFORMATION -> NotificationLevel.INFORMATION;
            case WATCH -> NotificationLevel.WATCH;
            case ADVISORY -> NotificationLevel.ADVISORY;
            case WARNING -> NotificationLevel.WARNING;
            case CRITICAL -> NotificationLevel.CRITICAL;
        };
    }
}
