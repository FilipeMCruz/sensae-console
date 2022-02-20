package sharespot.services.identitymanagementbackend.domainservices.service;

import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.domain.Domain;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;

public class PermissionsValidator {

    public static void verifyPermissions(Tenant actor, Domain domain) {
        var parentDomainIds = domain.getPath().path();
        if (actor.getDomains().stream().noneMatch(parentDomainIds::contains)) {
            throw new NotValidException("No permissions");
        }
    }
}
