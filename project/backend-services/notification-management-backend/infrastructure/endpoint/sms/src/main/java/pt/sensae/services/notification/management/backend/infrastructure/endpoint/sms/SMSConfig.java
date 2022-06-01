package pt.sensae.services.notification.management.backend.infrastructure.endpoint.sms;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SMSConfig {

    @Value("${sensae.sms.twilio.account.sid}")
    public String accountId;

    @Value("${sensae.sms.twilio.auth.token}")
    public String authToken;

    @Value("${sensae.sms.activate}")
    public boolean activate;

    @Bean
    public void twilio() {
        if (activate)
            Twilio.init(accountId, authToken);
    }
}
