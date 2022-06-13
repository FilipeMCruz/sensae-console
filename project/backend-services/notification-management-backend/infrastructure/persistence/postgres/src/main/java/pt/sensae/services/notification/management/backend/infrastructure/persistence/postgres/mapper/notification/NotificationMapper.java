package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.mapper.notification;

import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationContext;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationLevel;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.notification.NotificationPostgres;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public class NotificationMapper {

    public static Notification daoToModel(NotificationPostgres postgres) {

        var domains = postgres.domains.length() <= 2 ? new HashSet<DomainId>() : Arrays.stream(postgres.domains.substring(1, postgres.domains.length() - 2)
                        .split(","))
                .map(UUID::fromString)
                .map(DomainId::of)
                .collect(Collectors.toSet());

        var devices = postgres.deviceIds.length() <= 2 ? new HashSet<UUID>() : Arrays.stream(postgres.deviceIds.substring(1, postgres.deviceIds.length() - 2)
                        .split(","))
                .map(UUID::fromString)
                .collect(Collectors.toSet());

        var datas = postgres.dataIds.length() <= 2 ? new HashSet<UUID>() : Arrays.stream(postgres.dataIds.substring(1, postgres.dataIds.length() - 2)
                        .split(","))
                .map(UUID::fromString)
                .collect(Collectors.toSet());

        var context = new NotificationContext(datas, devices, Domains.of(domains), postgres.other);

        var type = ContentType.of(postgres.category, postgres.subCategory, NotificationLevel.valueOf(postgres.level));
        return Notification.of(UUID.fromString(postgres.id), type, postgres.description, postgres.reportedAt, context);
    }

    public static NotificationPostgres modelToDao(Notification model) {
        var dao = new NotificationPostgres();

        dao.id = model.id().toString();
        dao.category = model.type().category();
        dao.level = model.type().level().name();
        dao.subCategory = model.type().subCategory();
        dao.description = model.description();
        dao.reportedAt = model.reportedAt();
        dao.dataIds = model.context()
                .dataIds()
                .stream()
                .map(UUID::toString)
                .collect(Collectors.joining(",", "{", "}"));

        dao.deviceIds = model.context()
                .deviceIds()
                .stream()
                .map(UUID::toString)
                .collect(Collectors.joining(",", "{", "}"));

        dao.domains = model.context()
                .domains()
                .value()
                .stream()
                .map(DomainId::value)
                .map(UUID::toString)
                .collect(Collectors.joining(",", "{", "}"));

        dao.other = model.context().other();

        return dao;
    }
}
