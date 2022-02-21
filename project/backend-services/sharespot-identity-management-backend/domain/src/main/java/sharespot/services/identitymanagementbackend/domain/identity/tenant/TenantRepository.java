package sharespot.services.identitymanagementbackend.domain.identity.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.List;
import java.util.Optional;

public interface TenantRepository {

    Optional<Tenant> findTenantById(TenantId id);

    Tenant relocateTenant(Tenant tenant);

    Tenant registerNewTenant(Tenant tenant);

    List<Tenant> getTenantsInDomain(DomainId domain);
}
