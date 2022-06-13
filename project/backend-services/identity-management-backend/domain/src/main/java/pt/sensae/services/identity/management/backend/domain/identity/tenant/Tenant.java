package pt.sensae.services.identity.management.backend.domain.identity.tenant;

import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record Tenant(TenantId oid, TenantName name, TenantEmail email, TenantPhoneNumber phoneNumber,
                     List<DomainId> domains) {

    public Tenant withNewName(TenantName name) {
        return new Tenant(this.oid, name, this.email, this.phoneNumber, this.domains);
    }

    public Tenant withNewPhoneNumber(TenantPhoneNumber phoneNumber) {
        return new Tenant(this.oid, this.name, this.email, phoneNumber, this.domains);
    }

    public boolean isAnonymous() {
        return this.email.value().isBlank() && this.phoneNumber.value().isBlank() && this.name.isAnonymous();
    }

    public boolean isNotAnonymous() {
        return !this.isAnonymous();
    }
}
