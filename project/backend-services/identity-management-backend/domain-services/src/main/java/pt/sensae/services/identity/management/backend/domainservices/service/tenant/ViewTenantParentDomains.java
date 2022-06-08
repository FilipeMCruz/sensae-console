package pt.sensae.services.identity.management.backend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.mapper.DomainResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainResult;

import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ViewTenantParentDomains {

    private final DomainRepository domainRepo;

    public ViewTenantParentDomains(DomainRepository domainRepo) {
        this.domainRepo = domainRepo;
    }

    public Stream<DomainResult> fetch(IdentityCommand identity) {
        var domainIds = identity.domains.stream().map(UUID::fromString).map(DomainId::new);
        return domainRepo.getDomains(domainIds)
                .map(DomainResultMapper::toResult);
    }
}
