package pt.sensae.services.notification.dispatcher.backend.domain;

import pt.sensae.services.notification.dispatcher.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.dispatcher.backend.domain.tenant.Tenant;

public record Recipient(Tenant tenant, Addressee addressee) {
}
