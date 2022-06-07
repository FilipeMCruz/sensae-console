package pt.sensae.services.identity.management.backend.domain.identity.permissions;

import java.util.HashSet;
import java.util.Set;

public record DomainPermissions(Set<PermissionType> values) {

    public static DomainPermissions of(Set<PermissionType> values) {
        return new DomainPermissions(values);
    }

    public static DomainPermissions empty() {
        return new DomainPermissions(new HashSet<>());
    }
}
