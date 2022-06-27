package pt.sensae.services.notification.management.backend.domain.tenant;

import pt.sensae.services.notification.management.backend.domain.Domains;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface TenantRepository {

    Stream<Tenant> findTenantsInDomains(Domains domains);

    Tenant index(Tenant tenant);

    void refresh(Set<Tenant> tenants);

    Stream<Tenant> findAll();
}
