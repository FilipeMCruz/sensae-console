package sharespot.services.identitymanagementbackend.domain.model.identity.domain;

import java.util.List;
import java.util.Optional;

public interface DomainRepository {

    Optional<Domain> findDomainById(DomainId id);

    List<Domain> getParentDomains(DomainId id);

    List<Domain> getChildDomains(DomainId id);

    Optional<Domain> moveDomain(DomainId toMove, DomainId newParent);

    void addDomain(Domain domain);

    void deleteDomainAndChilds(DomainId id);
}
