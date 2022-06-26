package pt.sensae.services.data.decoder.slave.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getDataQueueName() {
        return "data-decoder-slave.data.any.queue";
    }

    public String getDataDecoderQueueName() {
        return "data-decoder-slave.internal.data-decoder.info." + id + ".queue";
    }
}
