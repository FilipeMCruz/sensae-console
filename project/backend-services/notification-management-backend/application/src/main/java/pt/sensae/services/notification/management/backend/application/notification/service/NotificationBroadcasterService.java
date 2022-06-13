package pt.sensae.services.notification.management.backend.application.notification.service;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.FullNotification;
import pt.sensae.services.notification.management.backend.domain.Recipient;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeConfig;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeRepository;
import pt.sensae.services.notification.management.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.management.backend.domain.tenant.TenantCache;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationBroadcasterService {

    private final NotificationPublisher publisher;

    private final TenantCache repository;

    private final AddresseeRepository addresseeRepository;

    private final UINotificationPublisher uiNotificationPublisher;

    public NotificationBroadcasterService(NotificationPublisher publisher,
                                          TenantCache repository,
                                          AddresseeRepository addresseeRepository,
                                          UINotificationPublisher uiNotificationPublisher) {
        this.publisher = publisher;
        this.repository = repository;
        this.addresseeRepository = addresseeRepository;
        this.uiNotificationPublisher = uiNotificationPublisher;
    }

    @PostConstruct
    private void init() {
        publisher.getSinglePublisher().subscribe(notification -> {
            var tenantsInDomains = repository.findTenantsInDomains(notification.context().domains())
                    .map(tenant -> new Recipient(tenant, addresseeRepository.findById(tenant.id())))
                    .collect(Collectors.toSet());

            var configUpdates = tenantsInDomains.stream()
                    .filter(recipient -> recipient.addressee().isNewType(notification))
                    .map(recipient -> new Addressee(recipient.addressee().id(), Set.of(
                            new AddresseeConfig(notification.type(), DeliveryType.UI, false),
                            new AddresseeConfig(notification.type(), DeliveryType.NOTIFICATION, false))))
                    .collect(Collectors.toSet());

            addresseeRepository.update(configUpdates);

            var uiRecipients = tenantsInDomains.stream()
                    .filter(recipient -> recipient.addressee().canSendVia(notification, DeliveryType.NOTIFICATION))
                    .map(Recipient::tenant)
                    .collect(Collectors.toSet());

            uiNotificationPublisher.send(FullNotification.of(uiRecipients, notification));
        });
    }
}
