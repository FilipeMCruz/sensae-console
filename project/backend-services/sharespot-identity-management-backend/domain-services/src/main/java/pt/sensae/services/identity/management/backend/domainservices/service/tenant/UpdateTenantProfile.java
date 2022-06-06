package pt.sensae.services.identity.management.backend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.*;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.TenantResult;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.UpdateTenantProfileCommand;
import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;

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
                .filter(Tenant::isNotAnonymous)
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        var updated = tenantToUpdate
                .withNewName(TenantName.of(command.name))
                .withNewPhoneNumber(TenantPhoneNumber.of(command.phoneNumber));

        var relocateTenant = tenantRepo.updateProfile(updated);

        publisher.publishUpdate(relocateTenant);

        return TenantResultMapper.toResult(relocateTenant);
    }
}
