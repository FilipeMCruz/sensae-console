package sharespot.services.identitymanagementbackend.domain.model.identity.tenant;

import sharespot.services.identitymanagementbackend.domain.model.identity.domain.DomainId;

import java.util.List;

public class Tenant {

    private final TenantEmail email;

    private final TenantName name;

    private final TenantId oid;

    private final List<DomainId> domains;

    public Tenant(TenantEmail email, TenantName name, TenantId oid, List<DomainId> domains) {
        this.email = email;
        this.name = name;
        this.oid = oid;
        this.domains = domains;
    }

    public List<DomainId> getDomains() {
        return domains;
    }
}
