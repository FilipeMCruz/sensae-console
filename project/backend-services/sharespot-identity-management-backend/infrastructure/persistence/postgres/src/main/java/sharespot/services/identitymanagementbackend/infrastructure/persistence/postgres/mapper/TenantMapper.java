package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantEmail;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantName;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.tenant.TenantPostgres;

import java.util.Arrays;
import java.util.UUID;

public class TenantMapper {

    public static TenantPostgres domainToPostgres(Tenant tenant) {
        var tenantPostgres = new TenantPostgres();
        tenantPostgres.email = tenant.getEmail().value();
        tenantPostgres.name = tenant.getName().value();
        tenantPostgres.oid = tenant.getOid().value().toString();
        tenantPostgres.domains = tenant.getDomains().stream().map(d -> d.value().toString()).toList().toArray(new String[0]);
        return tenantPostgres;
    }

    public static Tenant postgresToDomain(TenantPostgres postgres) {
        var name = TenantName.of(postgres.name);
        var oid = TenantId.of(UUID.fromString(postgres.oid));
        var email = TenantEmail.of(postgres.email);
        var domains = Arrays.stream(postgres.domains).map(d -> DomainId.of(UUID.fromString(d))).toList();
        return new Tenant(oid, name, email, domains);
    }
}
