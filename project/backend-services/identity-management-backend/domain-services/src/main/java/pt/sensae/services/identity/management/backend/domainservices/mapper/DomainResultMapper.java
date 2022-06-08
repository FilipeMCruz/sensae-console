package pt.sensae.services.identity.management.backend.domainservices.mapper;

import pt.sensae.services.identity.management.backend.domain.identity.domain.Domain;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainResult;

public class DomainResultMapper {

    public static DomainResult toResult(Domain domain) {
        var domainResult = new DomainResult();
        domainResult.domainId = domain.getOid().value();
        domainResult.domainName = domain.getName().value();
        domainResult.path = domain.getPath()
                .path()
                .stream()
                .map(d -> d.value().toString())
                .toList();
        domainResult.permissions = PermissionsMapper.toResult(domain.getPermissions().values().stream()).toList();
        return domainResult;
    }
}
