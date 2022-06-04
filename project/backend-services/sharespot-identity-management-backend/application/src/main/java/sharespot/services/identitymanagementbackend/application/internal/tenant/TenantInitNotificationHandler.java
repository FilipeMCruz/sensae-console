package sharespot.services.identitymanagementbackend.application.internal.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domainservices.service.tenant.CollectAllTenants;

@Service
public class TenantInitNotificationHandler {

    private final CollectAllTenants collector;

    private final TenantIdentitySyncHandler handler;

    private final TenantIdentityMapper mapper;

    public TenantInitNotificationHandler(CollectAllTenants collector, TenantIdentitySyncHandler handler, TenantIdentityMapper mapper) {
        this.collector = collector;
        this.handler = handler;
        this.mapper = mapper;
    }

    public void publishCurrentState() {
        var collect = collector.collect();
        handler.publishState(collect.map(mapper::domainToDto));
    }
}
