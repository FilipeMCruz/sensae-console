package sharespot.services.identitymanagementbackend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.*;
import sharespot.services.identitymanagementbackend.domainservices.mapper.TenantResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.TenantResult;

import java.util.List;
import java.util.UUID;

@Service
public class AuthenticateTenant {

    private final TenantRepository tenantRepo;

    private final DomainRepository domainRepo;

    private final TenantUpdateEventPublisher publisher;

    public AuthenticateTenant(TenantRepository tenantRepo, DomainRepository domainRepo, TenantUpdateEventPublisher publisher) {
        this.tenantRepo = tenantRepo;
        this.domainRepo = domainRepo;
        this.publisher = publisher;
    }

    public TenantResult execute(IdentityQuery command) {
        var tenant = tenantRepo.findTenantByEmail(TenantEmail.of(command.preferredUsername))
                .orElseGet(() -> newTenant(command));
        var domains = domainRepo.getDomains(tenant.domains().stream());
        return TenantResultMapper.toResult(tenant, domains);
    }

    private Tenant newTenant(IdentityQuery command) {
        var tenant = new Tenant(
                TenantId.of(UUID.randomUUID()),
                new TenantName(command.name),
                new TenantEmail(command.preferredUsername),
                List.of(domainRepo.getUnallocatedRootDomain().getOid()));
        var newTenant = tenantRepo.registerNewTenant(tenant);

        publisher.publishUpdate(newTenant);

        return newTenant;
    }
}
