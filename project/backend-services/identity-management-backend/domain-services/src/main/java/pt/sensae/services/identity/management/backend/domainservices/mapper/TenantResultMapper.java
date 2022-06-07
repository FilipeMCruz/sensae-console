package pt.sensae.services.identity.management.backend.domainservices.mapper;

import pt.sensae.services.identity.management.backend.domain.identity.domain.Domain;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.DomainPermissions;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.*;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.TenantResult;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TenantResultMapper {

    public static TenantIdentity toDomain(IdentityCommand command) {
        var domainsId = command.domains.stream().map(UUID::fromString).map(DomainId::of).toList();
        var tenant = new Tenant(TenantId.of(command.oid), TenantName.of(command.name), TenantEmail.of(command.email), TenantPhoneNumber.empty(), domainsId);
        var permissions = PermissionsMapper.toDomain(command.permissions.stream()).collect(Collectors.toSet());
        return new TenantIdentity(tenant, DomainPermissions.of(permissions));
    }

    public static TenantResult toResult(Tenant tenant) {
        var identityResult = new TenantResult();
        identityResult.oid = tenant.oid().value();
        identityResult.email = tenant.email().value();
        identityResult.phoneNumber = tenant.phoneNumber().value();
        identityResult.name = tenant.name().value();
        identityResult.domains = tenant.domains().stream().map(DomainId::value).toList();
        return identityResult;
    }

    public static TenantResult toResult(Tenant tenant, Stream<Domain> domains) {
        var identityResult = new TenantResult();
        identityResult.email = tenant.email().value();
        identityResult.name = tenant.name().value();
        identityResult.phoneNumber = tenant.phoneNumber().value();
        identityResult.oid = tenant.oid().value();
        identityResult.domains = tenant.domains().stream().map(DomainId::value).toList();

        var domainResults = domains.map(DomainResultMapper::toResult).toList();

        identityResult.parentDomains = domainResults.stream()
                .map(d -> d.path)
                .flatMap(Collection::stream)
                .map(UUID::fromString).filter(d -> !identityResult.domains.contains(d))
                .toList();

        identityResult.permissions = domainResults.stream()
                .map(d -> d.permissions)
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        return identityResult;
    }
}
