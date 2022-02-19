package sharespot.services.identitymanagementbackend.domain.identity.domain;

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

    private final DomainPath domainPath;

    public Domain(DomainName name, DomainId id, DomainPath domainPath) {
        this.name = name;
        this.id = id;
        this.domainPath = domainPath;
    }

    public DomainId getId() {
        return id;
    }

    public DomainName getName() {
        return name;
    }

    public DomainPath getDomainPath() {
        return domainPath;
    }

    public boolean isRoot() {
        return domainPath.path().size() == 1;
    }
}
