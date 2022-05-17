package sharespot.services.identitymanagementbackend.domain.identity.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.List;

public record Tenant(TenantId oid, TenantName name, TenantEmail email, List<DomainId> domains) {

}
