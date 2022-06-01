package pt.sensae.services.notification.management.backend.infrastructure.endpoint.sms;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.notification.service.SMSNotificationService;
import pt.sensae.services.notification.management.backend.domain.FullNotification;

@Service
public class SMSNotificationServiceImpl implements SMSNotificationService {

    Logger logger = LoggerFactory.getLogger(SMSNotificationServiceImpl.class);

    @Value("${sensae.sms.sender.number}")
    public String senderNumber;

    @Value("${sensae.sms.activate}")
    public boolean activate;

    @Override
    public void send(FullNotification notification) {
        notification.recipients().stream().filter(r -> !r.contacts().phone().trim().isEmpty()).forEach(recipient -> {
            logger.info("Sending SMS to {}. Content: {}", recipient.name().value(), notification.notification()
                    .asContent());
            sendSMS(recipient.contacts().phone(), notification.notification().asContent());
        });
    }

    private void sendSMS(String number, String content) {
        if (activate) Message.creator(new PhoneNumber(number), new PhoneNumber(senderNumber), content).create();
    }
}
