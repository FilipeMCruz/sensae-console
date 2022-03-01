package sharespot.services.identitymanagementbackend.domain.identity.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.permissions.DomainPermissions;

public record TenantIdentity(Tenant tenant, DomainPermissions permissions) {
}
