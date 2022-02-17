package sharespot.services.identitymanagementbackend.domain.model.identity.tenant;

import sharespot.services.identitymanagementbackend.domain.model.identity.domain.DomainId;

import java.util.List;
import java.util.Optional;

public interface TenantRepository {

    Optional<Tenant> findTenantById(TenantId id);
    
    Tenant moveTenant(Tenant tenant);

    Tenant registerNewTenant(Tenant tenant);

    List<Tenant> getTenantsInDomain(DomainId domain);

    List<Tenant> getTenantsInDomainAndSubDomains(DomainId domain);
}
