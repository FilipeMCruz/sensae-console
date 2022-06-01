package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.mapper;

import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.tenant.Tenant;
import pt.sensae.services.notification.management.backend.domain.tenant.TenantContacts;
import pt.sensae.services.notification.management.backend.domain.tenant.TenantName;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.model.TenantIdentityDTO;

import java.util.stream.Collectors;

public class TenantIdentityMapper {

    public static Tenant dtoToDomain(TenantIdentityDTO identity) {
        var collect = identity.domains.stream().map(DomainId::new).collect(Collectors.toSet());
        return new Tenant(AddresseeId.of(identity.id), TenantName.of(identity.name), TenantContacts.of(identity.email, identity.phoneNumber), Domains.of(collect));
    }
}
