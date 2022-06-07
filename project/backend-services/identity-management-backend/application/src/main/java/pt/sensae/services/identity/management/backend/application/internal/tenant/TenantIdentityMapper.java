package pt.sensae.services.identity.management.backend.application.internal.tenant;

import pt.sensae.services.identity.management.backend.domain.identity.tenant.Tenant;

public interface TenantIdentityMapper {

    TenantIdentityDTO domainToDto(Tenant tenant);
}
