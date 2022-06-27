package pt.sensae.services.data.processor.master.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getTransformationRequestQueueName() {
        return "data-processor-master.internal.data-transformation.request.queue";
    }

    public String getTransformationPingQueueName() {
        return "data-processor-master.internal.data-transformation.ping." + id + ".queue";
    }
}
