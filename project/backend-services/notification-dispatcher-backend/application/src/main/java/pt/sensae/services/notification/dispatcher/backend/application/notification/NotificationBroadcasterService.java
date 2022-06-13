package pt.sensae.services.notification.dispatcher.backend.application.notification;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.domain.FullNotification;
import pt.sensae.services.notification.dispatcher.backend.domain.Recipient;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.AddresseeRepository;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.dispatcher.backend.domain.tenant.TenantRepository;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Service
public class NotificationBroadcasterService {

    private final NotificationPublisher publisher;

    private final TenantRepository repository;

    private final AddresseeRepository addresseeRepository;

    private final EmailNotificationService emailService;

    private final SMSNotificationService smsService;


    public NotificationBroadcasterService(NotificationPublisher publisher,
                                          TenantRepository repository,
                                          AddresseeRepository addresseeRepository,
                                          EmailNotificationService emailService,
                                          SMSNotificationService smsService) {
        this.publisher = publisher;
        this.repository = repository;
        this.addresseeRepository = addresseeRepository;
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @PostConstruct
    private void init() {
        publisher.getSinglePublisher().subscribe(notification -> {
            var tenantsInDomains = repository.findTenantsInDomains(notification.context().domains())
                    .map(tenant -> new Recipient(tenant, addresseeRepository.findById(tenant.id())))
                    .filter(recipient -> recipient.addressee() != null)
                    .collect(Collectors.toSet());

            var smsRecipients = tenantsInDomains.stream()
                    .filter(recipient -> recipient.addressee().canSendVia(notification, DeliveryType.SMS))
                    .map(Recipient::tenant)
                    .collect(Collectors.toSet());

            var emailRecipients = tenantsInDomains.stream()
                    .filter(recipient -> recipient.addressee().canSendVia(notification, DeliveryType.EMAIL))
                    .map(Recipient::tenant)
                    .collect(Collectors.toSet());

            if(!smsRecipients.isEmpty()) smsService.send(FullNotification.of(smsRecipients, notification));
            if(!emailRecipients.isEmpty()) emailService.send(FullNotification.of(emailRecipients, notification));
        });
    }
}
