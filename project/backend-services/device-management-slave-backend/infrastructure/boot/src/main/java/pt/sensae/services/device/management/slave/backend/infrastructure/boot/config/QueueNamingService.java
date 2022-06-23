package pt.sensae.services.device.management.slave.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getDataQueueName() {
        return "device-management-slave.data.any.queue";
    }

    public String getDeviceManagementQueueName() {
        return "device-management-slave.internal.device-management.info." + id + ".queue";
    }
}
