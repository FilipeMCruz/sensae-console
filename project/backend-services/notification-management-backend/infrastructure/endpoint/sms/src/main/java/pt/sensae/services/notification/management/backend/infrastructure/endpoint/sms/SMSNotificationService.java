package pt.sensae.services.notification.management.backend.infrastructure.endpoint.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.FullNotification;

@Service
public class SMSNotificationService implements pt.sensae.services.notification.management.backend.application.notification.service.SMSNotificationService {

    Logger logger = LoggerFactory.getLogger(SMSNotificationService.class);

    @Override
    public void send(FullNotification notification) {
        notification.recipients().forEach(recipient -> {
            logger.info("Sending SMS to {}. Content: {}", recipient.name().value(), notification.notification()
                    .description());

        });
    }
}
