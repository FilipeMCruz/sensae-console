package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationBasicQuery;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationTemporalQuery;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationRepository;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.mapper.notification.NotificationMapper;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository.NotificationRepositoryPostgres;

import java.util.List;
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
    public Stream<Notification> find(NotificationTemporalQuery query) {
        var domainIds = extractDomains(query.domains());

        var configs = extractConfigs(query.configs());

        var collect = repositoryPostgres.findOldWithDomains(domainIds, query.start(), query.end(), configs, query.limit())
                .map(NotificationMapper::daoToModel).collect(Collectors.toSet());
        return collect.stream();
    }

    @Override
    @Transactional
    public Stream<Notification> find(NotificationBasicQuery query) {
        var domainIds = extractDomains(query.domains());

        var configs = extractConfigs(query.configs());

        var collect = repositoryPostgres.findOldWithDomainsAndConfigs(domainIds, configs, query.limit())
                .map(NotificationMapper::daoToModel).collect(Collectors.toSet());

        return collect.stream();
    }

    @Override
    public void save(Notification notification) {
        repositoryPostgres.save(NotificationMapper.modelToDao(notification));
    }

    private String extractDomains(Domains query) {
        return query
                .value()
                .stream()
                .map(DomainId::value)
                .map(UUID::toString)
                .collect(Collectors.joining(",", "{", "}"));
    }

    private String extractConfigs(List<ContentType> query) {
        return query
                .stream()
                .map(contentType -> contentType.category() + "-" + contentType.subCategory() + "-" + contentType.level()
                        .name())
                .collect(Collectors.joining(",", "{", "}"));
    }
}
