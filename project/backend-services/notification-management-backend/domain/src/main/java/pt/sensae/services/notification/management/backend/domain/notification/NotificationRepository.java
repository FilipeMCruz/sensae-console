package pt.sensae.services.notification.management.backend.domain.notification;

import java.util.stream.Stream;

public interface NotificationRepository {

    Stream<Notification> find(NotificationQuery query);

    void save(Notification notification);
}
