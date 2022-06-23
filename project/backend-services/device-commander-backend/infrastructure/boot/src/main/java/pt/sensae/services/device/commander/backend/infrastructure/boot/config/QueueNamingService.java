package pt.sensae.services.device.commander.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getCommandQueueName() {
        return "device-commander.command.any.queue";
    }

    public String getDeviceManagementQueueName() {
        return "device-commander.internal.device-management.info." + id + ".queue";
    }
}
