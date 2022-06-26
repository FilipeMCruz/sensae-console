package pt.sensae.services.device.ownership.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getAlertQueueName() {
        return "device-ownership.alert.any.queue";
    }

    public String getDataQueueName() {
        return "device-ownership.data.any.queue";
    }

    public String getDeviceOwnershipQueueName() {
        return "device-ownership.internal.device-identity.info." + id + ".queue";
    }
}
