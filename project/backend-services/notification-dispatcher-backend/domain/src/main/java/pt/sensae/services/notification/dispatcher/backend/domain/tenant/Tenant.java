package pt.sensae.services.notification.dispatcher.backend.domain.tenant;

import pt.sensae.services.notification.dispatcher.backend.domain.Domains;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.AddresseeId;

import java.util.Objects;

public record Tenant(AddresseeId id, TenantName name, TenantContacts contacts, Domains domains) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tenant tenant = (Tenant) o;

        return Objects.equals(id, tenant.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
