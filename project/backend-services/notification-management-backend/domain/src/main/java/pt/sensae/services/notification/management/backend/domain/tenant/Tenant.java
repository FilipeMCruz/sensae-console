package pt.sensae.services.notification.management.backend.domain.tenant;

import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;

public record Tenant(AddresseeId id, TenantName name, TenantContacts contacts, Domains domains) {
}
