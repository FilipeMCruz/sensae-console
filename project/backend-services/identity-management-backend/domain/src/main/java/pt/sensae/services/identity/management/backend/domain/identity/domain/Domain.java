package pt.sensae.services.identity.management.backend.domain.identity.domain;

import pt.sensae.services.identity.management.backend.domain.identity.permissions.DomainPermissions;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A Domain represents a department in a hierarchical organization structure.
 * An organization is composed of several domains in a tree like format.
 * Any "organization" is composed of the following static domains:
 * <p>
 * lvl 1 - root
 * <p>
 * lvl 2 - root/unallocated             root/{{others}}
 * <p>
 * lvl 3 - root/{{others}}/unallocated     root/{{others}}/{{sub-others}}
 * <p>
 * Rules:
 * <p>
 * - root domain has full control over everything.
 * <p>
 * - root/unallocated have access to nothing (stores people that have not been added to any company)
 * <p>
 * - root/{{others}}/unallocated stores people that have not been assigned any organization department and don't have access to anything.
 * <p>
 * - new people are sent to the root/unallocated domain and then moved to root/{{others}}/unallocated depending on the organization that they belong.
 * <p>
 * - new devices are sent to the root domain and then moved to root/{{others}} depending on the organization that they belong to.
 */
public class Domain {

    private final DomainName name;

    private final DomainId id;

    private final DomainPath path;

    private final DomainPermissions permissions;

    public Domain(DomainId oid, DomainName name, DomainPath path, DomainPermissions permissions) {
        this.name = name;
        this.id = oid;
        this.path = path;
        this.permissions = permissions;
    }

    public boolean same(Domain domain) {
        return name.equals(domain.name);
    }

    public DomainId getOid() {
        return id;
    }

    public DomainName getName() {
        return name;
    }

    public DomainPath getPath() {
        return path;
    }

    public DomainPermissions getPermissions() {
        return permissions;
    }

    public boolean isRoot() {
        return path.path().size() == 1;
    }

    public boolean isPublic() {
        return path.path().size() == 2 && name.isPublic();
    }

    public boolean isValidParentDomain() {
        return !isPublic() && !isUnallocated();
    }

    public boolean isUnallocated() {
        return name.isUnallocated();
    }

    public static Domain unallocated(Domain parent) {
        var name = DomainName.UNALLOCATED;
        var id = DomainId.of(UUID.randomUUID());
        var unallocatedDomainPath = new ArrayList<>(parent.path.path());
        unallocatedDomainPath.add(id);
        var path = DomainPath.of(unallocatedDomainPath);
        return new Domain(id, name, path, DomainPermissions.empty());
    }
}
