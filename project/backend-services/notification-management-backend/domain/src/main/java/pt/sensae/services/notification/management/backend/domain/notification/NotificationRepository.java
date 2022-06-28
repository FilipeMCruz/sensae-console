package pt.sensae.services.notification.management.backend.domain.notification;

import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;

import java.util.stream.Stream;

public interface NotificationRepository {

    Stream<Notification> find(NotificationTemporalQuery query);

    Stream<Notification> find(NotificationBasicQuery query);

    void save(Notification notification);

    void registerReadNotification(NotificationId notification, AddresseeId id);
}
