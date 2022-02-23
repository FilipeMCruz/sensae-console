package sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.Domain;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainRepository;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.mapper.DomainMapper;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.repository.DomainRepositoryPostgres;

import java.util.List;

@Repository
public class DomainRepositoryImpl implements DomainRepository {

    private final DomainRepositoryPostgres repository;

    public DomainRepositoryImpl(DomainRepositoryPostgres repository) {
        this.repository = repository;
    }

    @Override
    public Domain getRootDomain() {
        return repository.findRoot()
                .map(DomainMapper::postgresToDomain)
                .orElseThrow(() -> new RuntimeException("Database not bootstrapped"));
    }

    @Override
    public List<Domain> findDomainsById(List<DomainId> ids) {
        return repository.findAllByOidIn(ids.stream().map(d -> d.value().toString()).toList())
                .stream()
                .map(DomainMapper::postgresToDomain)
                .toList();
    }
}
