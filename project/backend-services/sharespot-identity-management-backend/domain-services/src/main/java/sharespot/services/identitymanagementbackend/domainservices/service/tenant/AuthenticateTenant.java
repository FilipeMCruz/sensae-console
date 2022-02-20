package sharespot.services.identitymanagementbackend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.*;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityResult;

import java.util.List;

@Service
public class AuthenticateTenant {

    private final TenantRepository tenantRepo;

    private final DomainRepository domainRepo;

    public AuthenticateTenant(TenantRepository tenantRepo, DomainRepository domainRepo) {
        this.tenantRepo = tenantRepo;
        this.domainRepo = domainRepo;
    }

    public IdentityResult execute(IdentityQuery command) {
        var tenant = tenantRepo.findTenantById(TenantId.of(command.oid))
                .orElseGet(() -> newTenant(command));
        return toResult(tenant);
    }

    private Tenant newTenant(IdentityQuery command) {
        return new Tenant(
                TenantId.of(command.oid),
                new TenantName(command.name),
                new TenantEmail(command.email),
                List.of(domainRepo.getUnallocatedRootDomain().getId()));
    }

    private IdentityResult toResult(Tenant tenant) {
        var identityResult = new IdentityResult();
        identityResult.email = tenant.getEmail().value();
        identityResult.name = tenant.getName().value();
        identityResult.oid = tenant.getOid().value();
        identityResult.domains = tenant.getDomains().stream().map(DomainId::value).toList();
        return identityResult;
    }
}
