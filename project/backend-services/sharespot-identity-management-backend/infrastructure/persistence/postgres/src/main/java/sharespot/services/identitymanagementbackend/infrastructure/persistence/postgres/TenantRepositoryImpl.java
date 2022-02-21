package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantRepository;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.mapper.TenantMapper;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository.TenantRepositoryPostgres;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class TenantRepositoryImpl implements TenantRepository {

    private final TenantRepositoryPostgres repository;

    public TenantRepositoryImpl(TenantRepositoryPostgres repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Tenant> findTenantById(TenantId id) {
        return repository.findByOid(id.value().toString())
                .map(TenantMapper::postgresToDomain);
    }

    @Override
    @Transactional
    public Tenant relocateTenant(Tenant tenant) {
        repository.deleteByOid(tenant.getOid().value().toString());
        var tenantPostgres = TenantMapper.domainToPostgres(tenant);
        return TenantMapper.postgresToDomain(repository.save(tenantPostgres));
    }

    @Override
    public Tenant registerNewTenant(Tenant tenant) {
        var tenantPostgres = TenantMapper.domainToPostgres(tenant);
        return TenantMapper.postgresToDomain(repository.save(tenantPostgres));
    }

    @Override
    public List<Tenant> getTenantsInDomain(DomainId domain) {
        return repository.findTenantsInDomain(domain.value().toString())
                .stream()
                .map(TenantMapper::postgresToDomain)
                .toList();
    }
}
