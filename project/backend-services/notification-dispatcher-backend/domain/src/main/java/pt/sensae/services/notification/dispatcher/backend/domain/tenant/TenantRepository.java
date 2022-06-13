package pt.sensae.services.notification.dispatcher.backend.domain.tenant;

import pt.sensae.services.notification.dispatcher.backend.domain.Domains;

import java.util.Set;
import java.util.stream.Stream;

public interface TenantRepository {

    Stream<Tenant> findTenantsInDomains(Domains domains);

    void index(Tenant tenant);

    void refresh(Set<Tenant> tenants);
}
