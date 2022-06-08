package pt.sensae.services.device.ownership.backend.domain.domain;

import java.util.stream.Stream;

public interface DomainRepository {

    Domain getRootDomain();

    Stream<Domain> findDomainsById(Stream<DomainId> ids);
}
