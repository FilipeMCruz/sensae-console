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

    @Query(value = "SELECT * FROM notification WHERE Cast(domains AS text[]) && Cast(:domains AS text[]) AND reported_at BETWEEN :openDate AND :closeDate ORDER BY reported_at DESC LIMIT :limit")
    Stream<NotificationPostgres> findOldWithDomains(@Param("domains") String domains, @Param("openDate") Instant openDate, @Param("closeDate") Instant closeDate, @Param("limit") int limit);

    @Query(value = "SELECT * FROM notification WHERE Cast(domains AS text[]) && Cast(:domains AS text[]) AND category||'-'||sub_category||'-'||level = any(Cast(:configs AS text[])) AND reported_at BETWEEN :openDate AND :closeDate ORDER BY reported_at DESC LIMIT :limit")
    Stream<NotificationPostgres> findOldWithDomains(@Param("domains") String domains, @Param("openDate") Instant openDate, @Param("closeDate") Instant closeDate, @Param("configs") String configs, @Param("limit") int limit);

    @Query(value = "SELECT * FROM notification WHERE Cast(domains AS text[]) && Cast(:domains AS text[]) AND category||'-'||sub_category||'-'||level = any(Cast(:configs AS text[])) ORDER BY reported_at DESC LIMIT :limit")
    Stream<NotificationPostgres> findOldWithDomainsAndConfigs(@Param("domains") String domains, @Param("configs") String configs, @Param("limit") int limit);
}
