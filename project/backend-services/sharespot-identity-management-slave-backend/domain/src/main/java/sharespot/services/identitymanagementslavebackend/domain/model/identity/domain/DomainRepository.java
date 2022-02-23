package sharespot.services.identitymanagementslavebackend.domain.model.identity.domain;

import java.util.List;

public interface DomainRepository {

    Domain getRootDomain();

    List<Domain> findDomainsById(List<DomainId> ids);
}
