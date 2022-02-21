package sharespot.services.identitymanagementbackend.domain.identity.domain;

import java.util.List;
import java.util.Optional;

public interface DomainRepository {

    Domain getUnallocatedRootDomain();

    Domain getRootDomain();

    Optional<Domain> findDomainById(DomainId id);

    List<Domain> getParentDomains(DomainId id);

    List<Domain> getChildDomains(DomainId id);

    void moveDomain(DomainId toMove, DomainId newParent);

    Domain addDomain(Domain domain);

    void deleteDomainAndChildren(DomainId id);
}
