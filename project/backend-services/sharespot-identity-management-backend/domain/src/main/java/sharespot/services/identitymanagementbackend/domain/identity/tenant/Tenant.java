package sharespot.services.identitymanagementbackend.domain.identity.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.List;

public class Tenant {

    private final TenantId oid;

    private final TenantEmail email;

    private final TenantName name;

    private final List<DomainId> domains;

    public Tenant(TenantId oid, TenantName name, TenantEmail email, List<DomainId> domains) {
        this.email = email;
        this.name = name;
        this.oid = oid;
        this.domains = domains;
    }

    public List<DomainId> getDomains() {
        return domains;
    }

    public TenantEmail getEmail() {
        return email;
    }

    public TenantName getName() {
        return name;
    }

    public TenantId getOid() {
        return oid;
    }
}
