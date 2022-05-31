package sharespot.services.identitymanagementbackend.application.internal.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;

public interface TenantIdentityMapper {

    TenantIdentityDTO domainToDto(Tenant tenant);
}
