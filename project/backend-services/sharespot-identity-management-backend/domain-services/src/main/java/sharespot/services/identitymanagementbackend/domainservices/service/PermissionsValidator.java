package sharespot.services.identitymanagementbackend.domainservices.service;

import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.domain.Domain;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantIdentity;

import java.util.List;

public class PermissionsValidator {

    public static void verifyPermissions(TenantIdentity actor, Domain domain, List<PermissionType> neededPermissions) {
        var parentDomainIds = domain.getPath().path();
        if (actor.tenant().domains().stream().noneMatch(parentDomainIds::contains)) {
            throw new NotValidException("No permissions");
        }
        if (!actor.permissions().values().containsAll(neededPermissions)) {
            throw new NotValidException("No permissions");
        }
    }
}
