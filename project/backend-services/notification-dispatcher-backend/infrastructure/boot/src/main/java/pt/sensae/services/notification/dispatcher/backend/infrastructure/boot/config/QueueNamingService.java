package pt.sensae.services.notification.dispatcher.backend.infrastructure.boot.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueNamingService {

    private final UUID id;

    public QueueNamingService() {
        this.id = UUID.randomUUID();
    }

    public String getNotificationQueueName() {
        return "notification-dispatcher.alert.any.queue";
    }

    public String getAttendeesConfigurationInfoQueueName() {
        return "notification-dispatcher.internal.addressee-configuration.info." + id + ".queue";
    }

    public String getAttendeesConfigurationSyncQueueName() {
        return "notification-dispatcher.internal.addressee-configuration.sync." + id + ".queue";
    }

    public String getAttendeesConfigurationInitQueueName() {
        return "notification-management.internal.addressee-configuration.init.queue";
    }

    public String getTenantIdentityInfoQueueName() {
        return "notification-dispatcher.internal.tenant-identity.info." + id + ".queue";
    }

    public String getTenantIdentitySyncQueueName() {
        return "notification-dispatcher.internal.tenant-identity.sync." + id + ".queue";
    }

    public String getTenantIdentityInitQueueName() {
        return "identity-management.internal.tenant-identity.init.queue";
    }
}
