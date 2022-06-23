package pt.sensae.services.data.processor.slave.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getDataQueueName() {
        return "data-processor-slave.data.any.queue";
    }

    public String getDataProcessorQueueName() {
        return "data-processor-slave.internal.data-processor.info." + id + ".queue";
    }
}
