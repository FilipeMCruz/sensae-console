package pt.sensae.services.notification.management.backend.application.notification.service;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.notification.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.notification.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.notification.management.backend.application.notification.mapper.NotificationMapper;
import pt.sensae.services.notification.management.backend.application.notification.model.ReadNotificationDTO;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationRepository;

@Service
public class NotificationReadRegister {

    private final TokenExtractor authHandler;

    private final NotificationMapper mapper;

    private final NotificationRepository repository;

    public NotificationReadRegister(TokenExtractor authHandler, NotificationMapper mapper, NotificationRepository repository) {
        this.authHandler = authHandler;
        this.mapper = mapper;
        this.repository = repository;
    }

    public ReadNotificationDTO register(ReadNotificationDTO command, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!(extract.permissions.contains("notification_management:past_data:read") ||
                extract.permissions.contains("notification_management:live_data:read")))
            throw new UnauthorizedException("No Permissions");

        var notificationId = mapper.toModel(command);

        repository.registerReadNotification(notificationId, AddresseeId.of(extract.oid));

        return command;
    }
}
