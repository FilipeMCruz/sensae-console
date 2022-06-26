package pt.sensae.services.notification.management.backend.domain.notification;

import java.util.stream.Stream;

public interface NotificationRepository {

    Stream<Notification> find(NotificationTemporalQuery query);

    Stream<Notification> find(NotificationBasicQuery query);

    void save(Notification notification);
}
