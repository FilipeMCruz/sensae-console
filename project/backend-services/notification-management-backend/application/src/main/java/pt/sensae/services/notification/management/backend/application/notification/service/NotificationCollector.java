package pt.sensae.services.notification.management.backend.application.notification.service;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.notification.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.notification.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.notification.management.backend.application.notification.mapper.NotificationHistoryQueryCommandMapper;
import pt.sensae.services.notification.management.backend.application.notification.mapper.NotificationMapper;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationDTO;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationHistoryQueryCommandDTO;
import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeRepository;
import pt.sensae.services.notification.management.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationQuery;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationRepository;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NotificationCollector {

    private final TokenExtractor authHandler;

    private final NotificationRepository repository;

    private final AddresseeRepository addresseeRepository;

    private final NotificationHistoryQueryCommandMapper commandMapper;

    private final NotificationMapper mapper;

    public NotificationCollector(TokenExtractor authHandler,
                                 NotificationRepository repository,
                                 AddresseeRepository addresseeRepository,
                                 NotificationHistoryQueryCommandMapper commandMapper,
                                 NotificationMapper mapper) {
        this.authHandler = authHandler;
        this.repository = repository;
        this.addresseeRepository = addresseeRepository;
        this.commandMapper = commandMapper;
        this.mapper = mapper;
    }

    public Stream<NotificationDTO> fetch(NotificationHistoryQueryCommandDTO command, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("alert_management:old_data:read"))
            throw new UnauthorizedException("No Permissions");

        var queryCommand = commandMapper.toDomain(command);

        var domainIds = extract.domains.stream()
                .map(UUID::fromString)
                .map(DomainId::of)
                .collect(Collectors.toSet());

        var notificationStream = repository.find(NotificationQuery.of(queryCommand.start, queryCommand.end, Domains.of(domainIds)));

        var addressee = addresseeRepository.findById(AddresseeId.of(extract.oid));

        return notificationStream.filter(notification -> addressee.canSendVia(notification, DeliveryType.NOTIFICATION))
                .map(mapper::toDto);
    }
}
