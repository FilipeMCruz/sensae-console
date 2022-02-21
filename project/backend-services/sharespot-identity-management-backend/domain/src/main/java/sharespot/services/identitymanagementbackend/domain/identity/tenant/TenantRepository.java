package sharespot.services.identitymanagementbackend.domain.identity.tenant;

import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository {

    Optional<Tenant> findTenantById(TenantId id);
    
    Tenant relocateTenant(Tenant tenant);

    Tenant registerNewTenant(Tenant tenant);

    List<Tenant> getTenantsInDomain(DomainId domain);
}
