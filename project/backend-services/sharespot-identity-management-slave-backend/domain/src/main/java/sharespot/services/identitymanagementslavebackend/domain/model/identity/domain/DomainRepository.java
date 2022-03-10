package sharespot.services.identitymanagementslavebackend.domain.model.identity.domain;

import java.util.stream.Stream;

public interface DomainRepository {

    Domain getRootDomain();

    Stream<Domain> findDomainsById(Stream<DomainId> ids);
}
