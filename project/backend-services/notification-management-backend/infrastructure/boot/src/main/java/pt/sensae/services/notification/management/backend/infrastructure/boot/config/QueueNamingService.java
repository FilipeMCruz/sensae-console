package pt.sensae.services.notification.management.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getNotificationQueueName() {
        return "notification-management.alert.any.queue";
    }

    public String getAttendeesConfigurationInitQueueName() {
        return "notification-management.internal.addressee-configuration.init.queue";
    }

    public String getTenantIdentityInfoQueueName() {
        return "notification-management.internal.tenant-identity.info." + id + ".queue";
    }

    public String getTenantIdentitySyncQueueName() {
        return "notification-management.internal.tenant-identity.sync." + id + ".queue";
    }

    public String getTenantIdentityInitQueueName() {
        return "identity-management.internal.tenant-identity.init.queue";
    }
}
