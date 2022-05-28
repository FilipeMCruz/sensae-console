package pt.sensae.services.notification.management.backend.infrastructure.endpoint.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.FullNotification;

@Service
public class EmailNotificationService implements pt.sensae.services.notification.management.backend.application.notification.service.EmailNotificationService {

    Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    @Override
    public void send(FullNotification notification) {
        notification.recipients().forEach(recipient -> {
            logger.info("Sending Email to {}. Content: {}", recipient.name().value(), notification.notification()
                    .description());
        });
    }
}
