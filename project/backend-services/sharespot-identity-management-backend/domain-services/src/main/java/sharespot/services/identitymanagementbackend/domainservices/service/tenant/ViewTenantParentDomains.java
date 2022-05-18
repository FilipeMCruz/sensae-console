package sharespot.services.identitymanagementbackend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domainservices.mapper.DomainResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainResult;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;

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
