package sharespot.services.identitymanagementbackend.domainservices.model.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.DomainPermissions;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.*;

import java.util.UUID;
import java.util.stream.Collectors;

public class TenantResultMapper {

    public static TenantIdentity toDomain(IdentityCommand command) {
        var domainsId = command.domains.stream().map(UUID::fromString).map(DomainId::of).toList();
        var tenant = new Tenant(TenantId.of(command.oid), TenantName.of(command.name), TenantEmail.of(command.email), domainsId);
        var permissions = command.permissions.stream().map(p -> switch (p) {
            case "device_records:records:read" -> PermissionType.READ_DEVICE_RECORDS;
            case "device_records:records:write" -> PermissionType.WRITE_DEVICE_RECORDS;
            case "data_transformations:transformations:read" -> PermissionType.READ_DATA_TRANSFORMATIONS;
            case "data_transformations:transformations:write" -> PermissionType.WRITE_DATA_TRANSFORMATIONS;
            case "fleet_management:read" -> PermissionType.READ_FLEET_MANAGEMENT;
            case "identity_management:domains:create" -> PermissionType.WRITE_DOMAINS;
            case "identity_management:domains:read" -> PermissionType.READ_DOMAINS;
            case "identity_management:tenant:read" -> PermissionType.READ_TENANT;
            case "identity_management:device:read" -> PermissionType.READ_DEVICE;
            case "identity_management:tenant:write" -> PermissionType.WRITE_TENANT;
            case "identity_management:device:write" -> PermissionType.WRITE_DEVICE;
            default -> throw new RuntimeException("Invalid Permissions");
        }).collect(Collectors.toSet());

        return new TenantIdentity(tenant, DomainPermissions.of(permissions));
    }

    public static TenantResult toResult(Tenant tenant) {
        var identityResult = new TenantResult();
        identityResult.oid = tenant.getOid().value();
        identityResult.email = tenant.getEmail().value();
        identityResult.name = tenant.getName().value();
        identityResult.domains = tenant.getDomains().stream().map(DomainId::value).toList();
        return identityResult;
    }
}
