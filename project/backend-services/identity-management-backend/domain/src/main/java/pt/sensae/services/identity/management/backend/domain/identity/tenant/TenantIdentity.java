package pt.sensae.services.identity.management.backend.domain.identity.tenant;

import pt.sensae.services.identity.management.backend.domain.identity.permissions.DomainPermissions;

public record TenantIdentity(Tenant tenant, DomainPermissions permissions) {
}
