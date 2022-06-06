package pt.sensae.services.identity.management.backend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.*;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityQuery;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.TenantResult;
import pt.sensae.services.identity.management.backend.domain.exceptions.UnhauthorizedException;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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
        var tenant = tenantRepo.findTenantByEmail(TenantEmail.of(command.getEmail()))
                .orElseGet(() -> newTenant(command));
        var domains = domainRepo.getDomains(tenant.domains().stream());
        var publicDomain = domainRepo.getPublicDomain();

        var allDomains = Stream.concat(domains, Stream.of(publicDomain));

        return TenantResultMapper.toResult(tenant, allDomains);
    }

    public TenantResult execute(IdentityCommand command) {
        var tenant = tenantRepo.findTenantByEmail(TenantEmail.of(command.email))
                .orElseThrow(UnhauthorizedException.withMessage("Invalid Credentials"));

        var domains = domainRepo.getDomains(tenant.domains().stream());
        var publicDomain = domainRepo.getPublicDomain();

        var allDomains = Stream.concat(domains, Stream.of(publicDomain));

        return TenantResultMapper.toResult(tenant, allDomains);
    }
    
    public TenantResult execute() {
        var tenant = tenantRepo.findAnonymous();
        var domains = domainRepo.getDomains(tenant.domains().stream());

        return TenantResultMapper.toResult(tenant, domains);
    }

    private Tenant newTenant(IdentityQuery command) {
        var tenant = new Tenant(
                TenantId.of(UUID.randomUUID()),
                TenantName.of(command.getName()),
                TenantEmail.of(command.getEmail()),
                TenantPhoneNumber.empty(),
                List.of(domainRepo.getUnallocatedRootDomain().getOid()));
        var newTenant = tenantRepo.registerNewTenant(tenant);

        publisher.publishUpdate(newTenant);

        return newTenant;
    }
}
