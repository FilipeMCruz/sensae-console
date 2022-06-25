package pt.sensae.services.device.management.master.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getDeviceManagementInitQueueName() {
        return "device-management-master.internal.device-management.init.queue";
    }

    public String getDeviceManagementRequestQueueName() {
        return "device-management-master.internal.device-management.request.queue";
    }

    public String getDeviceIdentityInfoQueueName() {
        return "device-management-master.internal.device-identity.info." + id + ".queue";
    }

    public String getDeviceIdentitySyncQueueName() {
        return "device-management-master.internal.device-identity.sync." + id + ".queue";
    }

    public String getDeviceIdentityInitQueueName() {
        return "identity-management-master.internal.device-identity.init.queue";
    }
}
