package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationQuery;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationRepository;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.mapper.notification.NotificationMapper;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository.NotificationRepositoryPostgres;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationRepositoryPostgres repositoryPostgres;

    public NotificationRepositoryImpl(NotificationRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    @Transactional
    public Stream<Notification> find(NotificationQuery query) {
        var domainIds = query.domains()
                .value()
                .stream()
                .map(DomainId::value)
                .map(UUID::toString)
                .collect(Collectors.joining(",", "{", "}"));

        var collect = repositoryPostgres.findOldWithDomains(domainIds, query.start(), query.end())
                .map(NotificationMapper::daoToModel).collect(Collectors.toSet());
        return collect.stream();
    }

    @Override
    public void save(Notification notification) {
        repositoryPostgres.save(NotificationMapper.modelToDao(notification));
    }
}
