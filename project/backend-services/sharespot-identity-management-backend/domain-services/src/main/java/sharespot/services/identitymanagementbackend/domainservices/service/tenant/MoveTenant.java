package sharespot.services.identitymanagementbackend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantRepository;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.*;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

import java.util.List;

@Service
public class MoveTenant {

    private final TenantRepository tenantRepo;

    private final DomainRepository domainRepo;

    public MoveTenant(TenantRepository tenantRepo, DomainRepository domainRepo) {
        this.tenantRepo = tenantRepo;
        this.domainRepo = domainRepo;
    }

    public TenantResult execute(PlaceTenantInDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);

        var domain = domainRepo.findDomainById(DomainId.of(command.newDomain))
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.WRITE_TENANT));

        var tenantToPlace = tenantRepo.findTenantById(TenantId.of(command.tenant))
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        tenantToPlace.getDomains().add(domain.getOid());

        var relocateTenant = tenantRepo.relocateTenant(tenantToPlace);
        return TenantResultMapper.toResult(relocateTenant);
    }

    public TenantResult execute(RemoveTenantFromDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);

        var domain = domainRepo.findDomainById(DomainId.of(command.domain))
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.WRITE_TENANT));

        var tenantToPlace = tenantRepo.findTenantById(TenantId.of(command.tenant))
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        tenantToPlace.getDomains().removeIf(d -> d.equals(domain.getOid()));

        if (tenantToPlace.getDomains().isEmpty()) {
            throw new NotValidException("Tenant has to belong to at least one Domain");
        }

        var relocateTenant = tenantRepo.relocateTenant(tenantToPlace);
        return TenantResultMapper.toResult(relocateTenant);
    }
}
