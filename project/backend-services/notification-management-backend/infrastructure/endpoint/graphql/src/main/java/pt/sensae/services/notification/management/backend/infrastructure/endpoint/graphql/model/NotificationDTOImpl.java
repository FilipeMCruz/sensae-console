package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.notification.management.backend.application.notification.model.NotificationDTO;

import java.util.Set;
import java.util.UUID;

public class NotificationDTOImpl implements NotificationDTO {

    public UUID id;

    public String reportedAt;

    public ContentTypeDTOImpl contentType;

    public String description;

    public Set<NotificationReaderDTOImpl> readers;
}
