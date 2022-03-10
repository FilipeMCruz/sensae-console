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
import java.util.UUID;
import java.util.stream.Stream;

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
        var permissions = domainRepo.getDomains(tenant.getDomains().stream())
                .map(d -> d.getPermissions().values())
                .flatMap(Collection::stream);
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

    private TenantResult toResult(Tenant tenant, Stream<PermissionType> permissions) {
        var identityResult = new TenantResult();
        identityResult.email = tenant.getEmail().value();
        identityResult.name = tenant.getName().value();
        identityResult.oid = tenant.getOid().value();
        identityResult.domains = tenant.getDomains().stream().map(DomainId::value).toList();
        identityResult.permissions = permissions.distinct().map(p ->
                switch (p) {
                    case READ_DEVICE_RECORDS -> "device_records:records:read";
                    case WRITE_DEVICE_RECORDS -> "device_records:records:write";
                    case READ_DATA_TRANSFORMATIONS -> "data_transformations:transformations:read";
                    case WRITE_DATA_TRANSFORMATIONS -> "data_transformations:transformations:write";
                    case READ_FLEET_MANAGEMENT -> "fleet_management:read";
                    case WRITE_DOMAINS -> "identity_management:domains:create";
                    case READ_DOMAINS -> "identity_management:domains:read";
                    case READ_TENANT -> "identity_management:tenant:read";
                    case READ_DEVICE -> "identity_management:device:read";
                    case WRITE_TENANT -> "identity_management:tenant:write";
                    case WRITE_DEVICE -> "identity_management:device:write";
                }).toList();
        return identityResult;
    }
}
