package sharespot.services.identitymanagementbackend.domain.identity.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface DomainRepository {

    Domain getUnallocatedRootDomain();

    Domain getRootDomain();

    Optional<Domain> findDomainById(DomainId id);

    Stream<Domain> getParentDomains(DomainId id);

    Stream<Domain> getChildDomains(DomainId id);

    Stream<Domain> getDomains(Stream<DomainId> ids);

    void moveDomain(DomainId toMove, DomainId newParent);

    Domain addDomain(Domain domain);

    void deleteDomainAndChildren(DomainId id);
}
