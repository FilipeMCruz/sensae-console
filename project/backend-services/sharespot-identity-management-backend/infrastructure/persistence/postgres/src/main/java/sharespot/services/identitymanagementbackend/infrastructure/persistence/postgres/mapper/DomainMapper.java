package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.domain.Domain;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainName;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainPath;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.DomainPermissions;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.domain.DomainPermissionPostgres;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.domain.DomainPostgres;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class DomainMapper {

    public static DomainPostgres domainToPostgres(Domain domain) {
        var domainPostgres = new DomainPostgres();
        domainPostgres.name = domain.getName().value();
        domainPostgres.oid = domain.getOid().value().toString();
        domainPostgres.path = domain.getPath().path().stream().map(d -> d.value().toString()).toList().toArray(new String[0]);
        domainPostgres.permissions = domain.getPermissions().values().stream().map(DomainPermissionPostgres::create).collect(Collectors.toSet());
        return domainPostgres;
    }

    public static Domain postgresToDomain(DomainPostgres postgres) {
        var name = DomainName.of(postgres.name);
        var oid = DomainId.of(UUID.fromString(postgres.oid));
        var domains = DomainPath.of(Arrays.stream(postgres.path).map(d -> DomainId.of(UUID.fromString(d))).toList());
        var permissions = DomainPermissions.of(postgres.permissions.stream().map(DomainPermissionPostgres::from).collect(Collectors.toSet()));
        if (domains.path().size() == 1) {
            permissions = DomainPermissions.of(Arrays.stream(PermissionType.values()).collect(Collectors.toSet()));
        }
        return new Domain(oid, name, domains, permissions);
    }
}
