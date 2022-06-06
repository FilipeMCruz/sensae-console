package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.Tenant;
import pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.model.tenant.TenantPostgres;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepositoryPostgres extends CrudRepository<TenantPostgres, Long> {

    Optional<TenantPostgres> findByOid(String tenantId);

    Optional<TenantPostgres> findByEmail(String tenantEmail);

    @Query(value = "SELECT * FROM tenant WHERE :domainId = ANY (domains)", nativeQuery = true)
    List<TenantPostgres> findTenantsInDomain(@Param("domainId") String domainId);

    @Query(value = "SELECT * FROM tenant WHERE :phone_number = '' AND :email = '' AND :name = 'Anonymous'", nativeQuery = true)
    Tenant findAnonymous();
}
