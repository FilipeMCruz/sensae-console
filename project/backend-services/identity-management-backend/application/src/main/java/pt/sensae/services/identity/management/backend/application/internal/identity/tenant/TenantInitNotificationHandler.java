package pt.sensae.services.identity.management.backend.application.internal.identity.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.service.tenant.CollectAllTenants;

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
