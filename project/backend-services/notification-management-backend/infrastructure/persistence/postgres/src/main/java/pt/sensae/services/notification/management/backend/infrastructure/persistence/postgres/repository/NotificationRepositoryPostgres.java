package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.notification.NotificationPostgres;

import java.time.Instant;
import java.util.stream.Stream;

@Repository
public interface NotificationRepositoryPostgres extends CrudRepository<NotificationPostgres, Long> {

    @Query(value = "SELECT * FROM notification WHERE Cast(domains AS text[]) && Cast(:domains AS text[]) AND reported_at BETWEEN :openDate AND :closeDate")
    Stream<NotificationPostgres> findOldWithDomains(@Param("domains") String domains, @Param("openDate") Instant openDate, @Param("closeDate") Instant closeDate);
}
