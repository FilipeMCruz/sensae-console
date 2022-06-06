package pt.sensae.services.identity.management.backend.domain.identity.tenant;

import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;

import java.util.Optional;
import java.util.stream.Stream;

public interface TenantRepository {

    Optional<Tenant> findTenantById(TenantId id);

    Optional<Tenant> findTenantByEmail(TenantEmail id);

    Tenant relocateTenant(Tenant tenant);

    Tenant registerNewTenant(Tenant tenant);

    Stream<Tenant> getTenantsInDomain(DomainId domain);

    Stream<Tenant> findAll();

    Tenant updateProfile(Tenant updated);

    Tenant findAnonymous();
}
