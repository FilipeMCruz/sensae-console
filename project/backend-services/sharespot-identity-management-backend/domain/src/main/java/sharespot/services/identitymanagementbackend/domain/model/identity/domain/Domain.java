package sharespot.services.identitymanagementbackend.domain.model.identity.domain;

public class Domain {

    private final DomainName name;

    private final DomainId id;

    private final DomainPath domainPath;

    public Domain(DomainName name, DomainId id, DomainPath domainPath) {
        this.name = name;
        this.id = id;
        this.domainPath = domainPath;
    }
}
