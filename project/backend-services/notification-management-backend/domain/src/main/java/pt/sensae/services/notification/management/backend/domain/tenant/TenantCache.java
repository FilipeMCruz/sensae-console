package pt.sensae.services.notification.management.backend.domain.tenant;

import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;

import java.util.Optional;
import java.util.stream.Stream;

public interface TenantCache {

    Optional<Tenant> findByAddresseeId(AddresseeId id);

    Stream<Tenant> findTenantsInDomains(Domains domains);

    Tenant index(Tenant tenant);
}
