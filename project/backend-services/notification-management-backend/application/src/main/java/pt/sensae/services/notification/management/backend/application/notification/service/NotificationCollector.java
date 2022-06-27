package pt.sensae.services.notification.management.backend.application.notification.service;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.notification.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.notification.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.notification.management.backend.application.notification.mapper.NotificationHistoryQueryCommandMapper;
import pt.sensae.services.notification.management.backend.application.notification.mapper.NotificationMapper;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationDTO;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationHistoryQueryCommandDTO;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationHistoryQueryCommandType;
import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeConfig;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeRepository;
import pt.sensae.services.notification.management.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationBasicQuery;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationTemporalQuery;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationRepository;
import pt.sensae.services.notification.management.backend.domain.tenant.Tenant;
import pt.sensae.services.notification.management.backend.domain.tenant.TenantRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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

    private final TenantRepository tenantRepository;

    public NotificationCollector(TokenExtractor authHandler,
                                 NotificationRepository repository,
                                 AddresseeRepository addresseeRepository,
                                 NotificationHistoryQueryCommandMapper commandMapper,
                                 NotificationMapper mapper,
                                 TenantRepository tenantRepository) {
        this.authHandler = authHandler;
        this.repository = repository;
        this.addresseeRepository = addresseeRepository;
        this.commandMapper = commandMapper;
        this.mapper = mapper;
        this.tenantRepository = tenantRepository;
    }

    public Stream<NotificationDTO> fetch(NotificationHistoryQueryCommandDTO command, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("notification_management:past_data:read"))
            throw new UnauthorizedException("No Permissions");

        var queryCommand = commandMapper.toDomain(command);

        var domains = Domains.of(extract.domains.stream()
                .map(UUID::fromString)
                .map(DomainId::of)
                .collect(Collectors.toSet()));

        var configs = addresseeRepository.findById(AddresseeId.of(extract.oid)).configs().stream()
                .filter(c -> !c.mute())
                .filter(c -> c.deliveryType().equals(DeliveryType.UI))
                .map(AddresseeConfig::contentType)
                .collect(Collectors.toList());

        var notifications = queryCommand.type.equals(NotificationHistoryQueryCommandType.TEMPORAL) ?
                repository.find(NotificationTemporalQuery.of(queryCommand.start, queryCommand.end, domains, configs))
                        .collect(Collectors.toSet()) :
                repository.find(NotificationBasicQuery.of(domains, configs)).collect(Collectors.toSet());

        // Compute distinct readers for all fetched notifications
        var addresses = notifications.stream()
                .map(Notification::readers)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        // Fetch only the tenants that the user knows
        var tenants = tenantRepository.findTenantsInDomains(domains)
                .filter(t -> addresses.contains(t.id()))
                .collect(Collectors.toSet());

        var tenantIds = tenants.stream().map(Tenant::id).collect(Collectors.toSet());

        // Add known tenants to corresponding notifications that they read
        return notifications.stream()
                .map(n -> mapper.toDto(n, tenants.stream()
                        .filter(t -> tenantIds.contains(t.id()))
                        .collect(Collectors.toSet())));
    }
}
