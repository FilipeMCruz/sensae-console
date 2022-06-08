package pt.sensae.services.identity.management.backend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.PlaceTenantInDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.RemoveTenantFromDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.TenantResult;
import pt.sensae.services.identity.management.backend.domainservices.service.PermissionsValidator;
import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.TenantId;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.TenantRepository;

import java.util.List;

@Service
public class MoveTenant {

    private final TenantRepository tenantRepo;

    private final DomainRepository domainRepo;

    private final TenantUpdateEventPublisher publisher;

    public MoveTenant(TenantRepository tenantRepo, DomainRepository domainRepo, TenantUpdateEventPublisher publisher) {
        this.tenantRepo = tenantRepo;
        this.domainRepo = domainRepo;
        this.publisher = publisher;
    }

    public TenantResult execute(PlaceTenantInDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);

        var domain = domainRepo.findDomainById(DomainId.of(command.newDomain))
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.EDIT_TENANT));

        var tenantToPlace = tenantRepo.findTenantById(TenantId.of(command.tenant))
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        tenantToPlace.domains().add(domain.getOid());

        var relocateTenant = tenantRepo.relocateTenant(tenantToPlace);

        publisher.publishUpdate(relocateTenant);

        return TenantResultMapper.toResult(relocateTenant);
    }

    public TenantResult execute(RemoveTenantFromDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);

        var domain = domainRepo.findDomainById(DomainId.of(command.domain))
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.EDIT_TENANT));

        var tenantToPlace = tenantRepo.findTenantById(TenantId.of(command.tenant))
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        tenantToPlace.domains().removeIf(d -> d.equals(domain.getOid()));

        if (tenantToPlace.domains().isEmpty()) {
            throw new NotValidException("Tenant has to belong to at least one Domain");
        }

        var relocateTenant = tenantRepo.relocateTenant(tenantToPlace);

        publisher.publishUpdate(relocateTenant);

        return TenantResultMapper.toResult(relocateTenant);
    }
}
