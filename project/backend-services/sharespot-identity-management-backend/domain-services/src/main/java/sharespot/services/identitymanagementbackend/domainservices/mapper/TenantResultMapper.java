package sharespot.services.identitymanagementbackend.domainservices.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.domain.Domain;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.DomainPermissions;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.*;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.TenantResult;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TenantResultMapper {

    public static TenantIdentity toDomain(IdentityCommand command) {
        var domainsId = command.domains.stream().map(UUID::fromString).map(DomainId::of).toList();
        var tenant = new Tenant(TenantId.of(command.oid), TenantName.of(command.name), TenantEmail.of(command.email), domainsId);
        var permissions = PermissionsMapper.toDomain(command.permissions.stream()).collect(Collectors.toSet());
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

    public static TenantResult toResult(Tenant tenant, Stream<Domain> domains) {
        var identityResult = new TenantResult();
        identityResult.email = tenant.getEmail().value();
        identityResult.name = tenant.getName().value();
        identityResult.oid = tenant.getOid().value();
        identityResult.domains = tenant.getDomains().stream().map(DomainId::value).toList();

        identityResult.permissions = domains.map(DomainResultMapper::toResult)
                .map(d -> d.permissions)
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        return identityResult;
    }
}
