package pt.sensae.services.data.decoder.master.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getDecoderRequestQueueName() {
        return "data-decoder-master.internal.data-decoder.request.queue";
    }

    public String getDecoderPingQueueName() {
        return "data-decoder-master.internal.data-decoder.ping." + id + ".queue";
    }
}
