package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.notification.management.backend.application.notification.model.NotificationHistoryQueryCommandDTO;

public class NotificationHistoryQueryDTOImpl implements NotificationHistoryQueryCommandDTO {

    public String startTime;

    public String endTime;
}
