package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.tenant.TenantPostgres;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface TenantRepositoryPostgres extends CrudRepository<TenantPostgres, Long> {

    Optional<TenantPostgres> findByOid(String tenantId);

    Optional<TenantPostgres> findByEmail(String tenantEmail);

    void deleteByOid(String tenantId);

    @Query(value = "SELECT * FROM tenant WHERE :domainId = ANY (domains)", nativeQuery = true)
    List<TenantPostgres> findTenantsInDomain(@Param("domainId") String domainId);
}
