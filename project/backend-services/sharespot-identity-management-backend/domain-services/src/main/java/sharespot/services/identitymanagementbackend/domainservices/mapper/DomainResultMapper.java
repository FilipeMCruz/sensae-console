package sharespot.services.identitymanagementbackend.domainservices.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.domain.Domain;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainResult;

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
