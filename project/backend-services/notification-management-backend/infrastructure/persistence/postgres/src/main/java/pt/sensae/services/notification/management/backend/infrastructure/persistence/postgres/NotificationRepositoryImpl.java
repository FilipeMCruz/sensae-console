package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;
import pt.sensae.services.notification.management.backend.domain.notification.*;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.mapper.notification.NotificationMapper;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.notification.NotificationPostgres;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.notification.ReadNotificationPostgres;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository.NotificationRepositoryPostgres;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository.ReadNotificationRepositoryPostgres;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationRepositoryPostgres repositoryPostgres;

    private final ReadNotificationRepositoryPostgres readNotificationRepositoryPostgres;

    public NotificationRepositoryImpl(NotificationRepositoryPostgres repositoryPostgres,
                                      ReadNotificationRepositoryPostgres readNotificationRepositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
        this.readNotificationRepositoryPostgres = readNotificationRepositoryPostgres;
    }

    @Override
    @Transactional
    public Stream<Notification> find(NotificationTemporalQuery query) {
        var domainIds = extractDomains(query.domains());

        var configs = extractConfigs(query.configs());

        var collect = repositoryPostgres.findOldWithDomains(domainIds, query.start(), query.end(), configs, query.limit())
                .collect(Collectors.toSet());

        return buildNotificationsWithWhoReadThem(collect);
    }

    @Override
    @Transactional
    public Stream<Notification> find(NotificationBasicQuery query) {
        var domainIds = extractDomains(query.domains());

        var configs = extractConfigs(query.configs());

        var collect = repositoryPostgres.findOldWithDomainsAndConfigs(domainIds, configs, query.limit())
                .collect(Collectors.toSet());

        return buildNotificationsWithWhoReadThem(collect);
    }

    @Override
    @Transactional
    public void save(Notification notification) {
        repositoryPostgres.save(NotificationMapper.modelToDao(notification));
    }

    @Override
    @Transactional
    public void registerReadNotification(NotificationId notificationId, AddresseeId addresseeId) {
        var postgres = NotificationMapper.modelToDao(notificationId, addresseeId);

        if (readNotificationRepositoryPostgres.findByIdAndTenant(postgres.id, postgres.tenant).isEmpty())
            readNotificationRepositoryPostgres.save(postgres);
    }

    private Stream<Notification> buildNotificationsWithWhoReadThem(Set<NotificationPostgres> collect) {
        var notificationIds = collect.stream().map(n -> n.id).collect(Collectors.joining(",", "{", "}"));

        var readNotifications = readNotificationRepositoryPostgres.findReadNotifications(notificationIds)
                .collect(Collectors.toMap(entry -> entry.id, entry -> {
                    var col = new HashSet<ReadNotificationPostgres>();
                    col.add(entry);
                    return col;
                }, (existingEntry, newEntry) -> {
                    existingEntry.addAll(newEntry);
                    return existingEntry;
                }));

        return collect.stream()
                .map(postgres -> NotificationMapper.daoToModel(postgres, readNotifications.get(postgres.id)));
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
