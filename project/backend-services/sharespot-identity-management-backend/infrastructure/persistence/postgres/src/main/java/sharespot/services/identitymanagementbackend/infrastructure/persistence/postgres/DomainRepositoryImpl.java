package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.Domain;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.mapper.DomainMapper;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository.DomainRepositoryPostgres;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return repository.findDomainParents(id.value().toString())
                .stream()
                .map(DomainMapper::postgresToDomain)
                .toList();
    }

    @Override
    public List<Domain> getChildDomains(DomainId id) {
        var byOid = repository.findByOid(id.value().toString());
        if (byOid.isEmpty()) return new ArrayList<>();
        return repository.findDomainChilds(byOid.get().path)
                .stream()
                .map(DomainMapper::postgresToDomain)
                .toList();
    }

    @Override
    public void moveDomain(DomainId toMove, DomainId newParent) {
        var byOid = repository.findByOid(toMove.value().toString());
        var parent = repository.findByOid(newParent.value().toString());
        if (byOid.isPresent() && parent.isPresent()) {
            repository.moveDomain(byOid.get().oid, parent.get().path);
        }
    }

    @Override
    public void addDomain(Domain domain) {
        var domainPostgres = DomainMapper.domainToPostgres(domain);
        repository.save(domainPostgres);
    }

    @Override
    public void deleteDomainAndChildren(DomainId id) {
        var byOid = repository.findByOid(id.value().toString());
        byOid.ifPresent(domainPostgres -> repository.deleteDomainChilds(domainPostgres.path));
    }
}
