package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.domain.Domain;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainName;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainPath;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.domain.DomainPostgres;

import java.util.Arrays;
import java.util.UUID;

public class DomainMapper {

    public static DomainPostgres domainToPostgres(Domain domain) {
        var domainPostgres = new DomainPostgres();
        domainPostgres.name = domain.getName().value();
        domainPostgres.oid = domain.getOid().value().toString();
        domainPostgres.path = domain.getPath().path().stream().map(d -> d.value().toString()).toList().toArray(new String[0]);
        return domainPostgres;
    }

    public static Domain postgresToDomain(DomainPostgres postgres) {
        var name = DomainName.of(postgres.name);
        var oid = DomainId.of(UUID.fromString(postgres.oid));
        var domains = DomainPath.of(Arrays.stream(postgres.path).map(d -> DomainId.of(UUID.fromString(d))).toList());
        return new Domain(oid, name, domains);
    }
}
