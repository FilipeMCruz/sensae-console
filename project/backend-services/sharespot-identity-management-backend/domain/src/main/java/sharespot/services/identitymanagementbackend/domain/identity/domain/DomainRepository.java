package sharespot.services.identitymanagementbackend.domain.identity.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomainRepository {

    Domain getUnallocatedRootDomain();

    Domain getRootDomain();

    Optional<Domain> findDomainById(DomainId id);

    List<Domain> getParentDomains(DomainId id);

    List<Domain> getChildDomains(DomainId id);

    Optional<Domain> moveDomain(DomainId toMove, DomainId newParent);

    void addDomain(Domain domain);

    void deleteDomainAndChilds(DomainId id);
}
