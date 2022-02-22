package sharespot.services.identitymanagementbackend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantRepository;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.PlaceTenantInDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.TenantResult;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.TenantResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

@Service
public class PlaceTenantInDomain {

    private final TenantRepository tenantRepo;

    private final DomainRepository domainRepo;

    public PlaceTenantInDomain(TenantRepository tenantRepo, DomainRepository domainRepo) {
        this.tenantRepo = tenantRepo;
        this.domainRepo = domainRepo;
    }

    public TenantResult execute(PlaceTenantInDomainCommand command, IdentityCommand identity) {
        var tenant = tenantRepo.findTenantById(TenantId.of(identity.oid))
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        var domain = domainRepo.findDomainById(DomainId.of(command.newDomain))
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain);

        var tenantToPlace = tenantRepo.findTenantById(TenantId.of(command.tenant))
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        tenantToPlace.getDomains().add(domain.getOid());
        var relocateTenant = tenantRepo.relocateTenant(tenantToPlace);
        return TenantResultMapper.toResult(relocateTenant);
    }
}
