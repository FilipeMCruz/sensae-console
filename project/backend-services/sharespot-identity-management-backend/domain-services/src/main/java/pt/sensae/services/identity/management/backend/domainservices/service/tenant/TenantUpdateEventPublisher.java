package pt.sensae.services.identity.management.backend.domainservices.service.tenant;

import pt.sensae.services.identity.management.backend.domain.identity.tenant.Tenant;

public interface TenantUpdateEventPublisher {

    void publishUpdate(Tenant domain);
    
}
