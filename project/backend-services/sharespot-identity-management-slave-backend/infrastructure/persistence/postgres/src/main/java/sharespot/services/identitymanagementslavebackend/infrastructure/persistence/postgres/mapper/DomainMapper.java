package sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.mapper;

import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.Domain;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainName;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainPath;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.model.domain.DomainPostgres;

import java.util.Arrays;
import java.util.UUID;

public class DomainMapper {

    public static Domain postgresToDomain(DomainPostgres postgres) {
        var name = DomainName.of(postgres.name);
        var oid = DomainId.of(UUID.fromString(postgres.oid));
        var domains = DomainPath.of(Arrays.stream(postgres.path).map(d -> DomainId.of(UUID.fromString(d))).toList());
        return new Domain(oid, name, domains);
    }
}
