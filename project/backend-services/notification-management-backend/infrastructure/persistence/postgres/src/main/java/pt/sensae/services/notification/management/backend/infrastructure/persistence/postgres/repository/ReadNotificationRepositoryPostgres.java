package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.notification.NotificationPostgres;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.notification.ReadNotificationPostgres;

import java.util.stream.Stream;

@Repository
public interface ReadNotificationRepositoryPostgres extends CrudRepository<ReadNotificationPostgres, Long> {

    @Query(value = "SELECT * FROM read WHERE id = any(Cast(:ids AS text[]))")
    Stream<ReadNotificationPostgres> findReadNotifications(@Param("ids") String notificationIds);
}
