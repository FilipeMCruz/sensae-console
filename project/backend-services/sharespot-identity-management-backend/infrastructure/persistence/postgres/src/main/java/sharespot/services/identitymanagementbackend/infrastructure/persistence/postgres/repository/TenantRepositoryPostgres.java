package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.tenant.TenantPostgres;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepositoryPostgres extends CrudRepository<TenantPostgres, Long> {

    Optional<TenantPostgres> findByOid(String tenantId);

    void deleteByOid(String tenantId);

    @Query(value = "SELECT t FROM tenant t WHERE :domainId in t.domains")
    List<TenantPostgres> findTenantsInDomain(@Param("domainId") String domainId);
}
