package sharespot.services.identitymanagementbackend.domainservices.model.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantEmail;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantName;

import java.util.UUID;

public class TenantIdentityMapper {

    public static Tenant toDomain(IdentityCommand command) {
        var domainsId = command.domains.stream().map(UUID::fromString).map(DomainId::of).toList();
        return new Tenant(TenantId.of(command.oid), TenantName.of(command.name), TenantEmail.of(command.email), domainsId);
    }
}
