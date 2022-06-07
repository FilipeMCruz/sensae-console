package pt.sensae.services.identity.management.backend.domainservices.service;

import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.identity.management.backend.domain.identity.domain.Domain;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.TenantIdentity;

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
