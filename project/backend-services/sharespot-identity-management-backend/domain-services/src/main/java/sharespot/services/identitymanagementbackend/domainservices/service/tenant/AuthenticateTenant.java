package sharespot.services.identitymanagementbackend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.*;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.TenantResult;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthenticateTenant {

    private final TenantRepository tenantRepo;

    private final DomainRepository domainRepo;

    public AuthenticateTenant(TenantRepository tenantRepo, DomainRepository domainRepo) {
        this.tenantRepo = tenantRepo;
        this.domainRepo = domainRepo;
    }

    public TenantResult execute(IdentityQuery command) {
        var tenant = tenantRepo.findTenantByEmail(TenantEmail.of(command.preferredUsername))
                .orElseGet(() -> newTenant(command));
        var permissions = domainRepo.getDomains(tenant.getDomains())
                .stream()
                .map(d -> d.getPermissions().values())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return toResult(tenant, permissions);
    }

    private Tenant newTenant(IdentityQuery command) {
        var tenant = new Tenant(
                TenantId.of(UUID.randomUUID()),
                new TenantName(command.name),
                new TenantEmail(command.preferredUsername),
                List.of(domainRepo.getUnallocatedRootDomain().getOid()));
        return tenantRepo.registerNewTenant(tenant);
    }

    private TenantResult toResult(Tenant tenant, Set<PermissionType> permissions) {
        var identityResult = new TenantResult();
        identityResult.email = tenant.getEmail().value();
        identityResult.name = tenant.getName().value();
        identityResult.oid = tenant.getOid().value();
        identityResult.domains = tenant.getDomains().stream().map(DomainId::value).toList();
        identityResult.permissions = permissions.stream().map(p ->
                switch (p) {
                    case READ_DEVICE_RECORDS -> "read:device_records";
                    case WRITE_DEVICE_RECORDS -> "write:device_records";
                    case READ_DATA_TRANSFORMATIONS -> "read:data_transformations";
                    case WRITE_DATA_TRANSFORMATIONS -> "write:data_transformations";
                    case READ_FLEET_MANAGEMENT -> "read:fleet_management";
                }).toList();
        return identityResult;
    }
}
