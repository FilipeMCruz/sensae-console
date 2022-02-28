package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.Domain;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.mapper.DomainMapper;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository.DomainRepositoryPostgres;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository.util.PostgresArrayMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Domain getUnallocatedRootDomain() {
        return repository.findRootUnallocated()
                .map(DomainMapper::postgresToDomain)
                .orElseThrow(() -> new RuntimeException("Database not bootstrapped"));
    }

    @Override
    public Optional<Domain> findDomainById(DomainId id) {
        return repository.findByOid(id.value().toString())
                .map(DomainMapper::postgresToDomain);
    }

    @Override
    public List<Domain> getParentDomains(DomainId id) {
        var byOid = repository.findByOid(id.value().toString());
        if (byOid.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.findDomainParents(PostgresArrayMapper.toArray(List.of(byOid.get().path)))
                .stream()
                .map(DomainMapper::postgresToDomain)
                .toList();
    }

    @Override
    public List<Domain> getChildDomains(DomainId id) {
        LoggerFactory.getLogger(DomainRepositoryImpl.class).info(id.value().toString());
        return repository.findDomainChilds(PostgresArrayMapper.toArray(id.value().toString()))
                .stream()
                .map(DomainMapper::postgresToDomain)
                .toList();
    }

    @Override
    public List<Domain> getDomains(List<DomainId> ids) {
        return repository.findAllByOidIsIn(ids.stream().map(d -> d.value().toString()).collect(Collectors.toList())).stream()
                .map(DomainMapper::postgresToDomain)
                .toList();
    }

    @Override
    public void moveDomain(DomainId toMove, DomainId newParent) {
        var parent = repository.findByOid(newParent.value().toString());
        parent.ifPresent(domainPostgres ->
                repository.moveDomain(toMove.value().toString(),
                        PostgresArrayMapper.toArray(toMove.value().toString()),
                        PostgresArrayMapper.toArray(List.of(domainPostgres.path))));
    }

    @Override
    public Domain addDomain(Domain domain) {
        var domainPostgres = DomainMapper.domainToPostgres(domain);
        var saved = repository.save(domainPostgres);
        return DomainMapper.postgresToDomain(saved);
    }

    @Override
    public void deleteDomainAndChildren(DomainId id) {
        repository.deleteDomainChilds(PostgresArrayMapper.toArray(id.value().toString()));
    }
}
