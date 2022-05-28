package sharespot.services.identitymanagementbackend.application.internal.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domainservices.service.tenant.CollectAllTenants;

@Service
public class TenantNotificationHandler {

    private final CollectAllTenants collector;

    private final TenantIdentitySyncHandler handler;

    public TenantNotificationHandler(CollectAllTenants collector, TenantIdentitySyncHandler handler) {
        this.collector = collector;
        this.handler = handler;
    }

    public void publishCurrentState() {
        var collect = collector.collect();
        handler.publishState(collect);
    }
}
