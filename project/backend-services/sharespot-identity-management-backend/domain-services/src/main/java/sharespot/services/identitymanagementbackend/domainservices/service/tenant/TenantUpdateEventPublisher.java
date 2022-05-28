package sharespot.services.identitymanagementbackend.domainservices.service.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;

public interface TenantUpdateEventPublisher {

    void publishUpdate(Tenant domain);
    
}
