package sharespot.services.identitymanagementbackend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantName;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantPhoneNumber;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantRepository;
import sharespot.services.identitymanagementbackend.domainservices.mapper.TenantResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.*;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

import java.util.List;

@Service
public class UpdateTenantProfile {

    private final TenantRepository tenantRepo;

    private final TenantUpdateEventPublisher publisher;

    public UpdateTenantProfile(TenantRepository tenantRepo, TenantUpdateEventPublisher publisher) {
        this.tenantRepo = tenantRepo;
        this.publisher = publisher;
    }

    public TenantResult execute(UpdateTenantProfileCommand command, IdentityCommand identity) {
        var tenantToUpdate = tenantRepo.findTenantById(TenantId.of(identity.oid))
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        var updated = tenantToUpdate
                .withNewName(TenantName.of(command.name))
                .withNewPhoneNumber(TenantPhoneNumber.of(command.phoneNumber));

        var relocateTenant = tenantRepo.updateProfile(updated);

        publisher.publishUpdate(relocateTenant);

        return TenantResultMapper.toResult(relocateTenant);
    }
}
