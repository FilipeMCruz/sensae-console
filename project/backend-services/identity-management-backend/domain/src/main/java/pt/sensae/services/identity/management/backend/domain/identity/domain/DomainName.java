package pt.sensae.services.identity.management.backend.domain.identity.domain;

public record DomainName(String value) {

    public boolean isUnallocated() {
        return "unallocated".equalsIgnoreCase(value);
    }

    public boolean isPublic() {
        return "public".equalsIgnoreCase(value);
    }

    public boolean isRoot() {
        return "root".equalsIgnoreCase(value);
    }

    public boolean isValidName() {
        return !isUnallocated() && !isPublic() && !isRoot();
    }

    public static final DomainName UNALLOCATED = DomainName.of("unallocated");

    public static DomainName of(String value) {
        return new DomainName(value);
    }

}
