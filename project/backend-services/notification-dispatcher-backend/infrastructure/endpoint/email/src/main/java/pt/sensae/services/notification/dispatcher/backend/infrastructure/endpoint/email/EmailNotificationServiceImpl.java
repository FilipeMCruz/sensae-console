package pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.application.notification.EmailNotificationService;
import pt.sensae.services.notification.dispatcher.backend.domain.FullNotification;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final Logger logger = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);

    @Value("${sensae.email.sender.account}")
    public String accountName;

    @Value("${sensae.email.subject}")
    public String subject;

    @Value("${sensae.email.activate}")
    public boolean activate;

    private final JavaMailSender emailSender;

    public EmailNotificationServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void send(FullNotification notification) {
        var recipients = notification.recipients()
                .stream()
                .filter(r -> !r.contacts().email().trim().isEmpty())
                .map(r -> r.contacts().email()).toArray(String[]::new);

        logger.info("Sending Email to {}. Content: {}", String.join(",", recipients), notification.notification()
                .asContent());
        if (activate) sendSimpleMessage(subject, notification.notification().asContent(), recipients);
    }


    public void sendSimpleMessage(String subject, String text, String[] to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(accountName);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}