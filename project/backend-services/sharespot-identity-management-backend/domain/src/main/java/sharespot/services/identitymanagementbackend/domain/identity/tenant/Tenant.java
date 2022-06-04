package sharespot.services.identitymanagementbackend.domain.identity.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.List;

public record Tenant(TenantId oid, TenantName name, TenantEmail email, TenantPhoneNumber phoneNumber,
                     List<DomainId> domains) {

    public Tenant withNewName(TenantName name) {
        return new Tenant(this.oid, name, this.email, this.phoneNumber, this.domains);
    }

    public Tenant withNewPhoneNumber(TenantPhoneNumber phoneNumber) {
        return new Tenant(this.oid, this.name, this.email, phoneNumber, this.domains);
    }
}
