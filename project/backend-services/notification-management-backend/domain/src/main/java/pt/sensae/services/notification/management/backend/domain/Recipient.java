package pt.sensae.services.notification.management.backend.domain;

import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.tenant.Tenant;

public record Recipient(Tenant tenant, Addressee addressee) {
}
