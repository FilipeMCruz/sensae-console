package pt.sensae.services.data.validator.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    public QueueNamingService() {
    }

    public String getDataQueueName() {
        return "data-validator.data.any.queue";
    }
}
