package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.Tenant;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.TenantEmail;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.TenantId;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.TenantRepository;
import pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.mapper.TenantMapper;
import pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.repository.TenantRepositoryPostgres;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class TenantRepositoryImpl implements TenantRepository {

    private final TenantRepositoryPostgres repository;

    public TenantRepositoryImpl(TenantRepositoryPostgres repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Optional<Tenant> findTenantById(TenantId id) {
        return repository.findByOid(id.value().toString())
                .map(TenantMapper::postgresToDomain)
                .filter(Tenant::isNotAnonymous);
    }

    @Override
    @Transactional
    public Optional<Tenant> findTenantByEmail(TenantEmail email) {
        return repository.findByEmail(email.value())
                .map(TenantMapper::postgresToDomain);
    }

    @Override
    @Transactional
    public Tenant relocateTenant(Tenant tenant) {
        var newDomains = TenantMapper.domainToPostgres(tenant).domains;
        repository.findByOid(tenant.oid().value().toString()).ifPresent(tenantPostgres -> {
            tenantPostgres.domains = newDomains;
            repository.save(tenantPostgres);
        });
        return tenant;
    }

    @Override
    @Transactional
    public Tenant registerNewTenant(Tenant tenant) {
        var tenantPostgres = TenantMapper.domainToPostgres(tenant);
        return TenantMapper.postgresToDomain(repository.save(tenantPostgres));
    }

    @Override
    @Transactional
    public Stream<Tenant> getTenantsInDomain(DomainId domain) {
        return repository.findTenantsInDomain(domain.value().toString())
                .stream()
                .map(TenantMapper::postgresToDomain)
                .filter(Tenant::isNotAnonymous);
    }

    @Override
    @Transactional
    public Stream<Tenant> findAll() {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .map(TenantMapper::postgresToDomain)
                .filter(Tenant::isNotAnonymous)
                .toList().stream();
    }

    @Override
    @Transactional
    public Tenant updateProfile(Tenant updated) {
        var tenantPostgres = repository.findByEmail(updated.email().value())
                .filter(d -> !d.email.isBlank() && !d.name.equalsIgnoreCase("Anonymous"));

        tenantPostgres.ifPresent(tenant -> {
            tenant.phoneNumber = updated.phoneNumber().value();
            tenant.name = updated.name().value();
            repository.save(tenant);
        });
        return updated;
    }

    @Override
    public Tenant findAnonymous() {
        return TenantMapper.postgresToDomain(repository.findAnonymous());
    }
}
