package sharespot.services.identitymanagementbackend.domain.identity.domain;

public record DomainName(String value) {

    public boolean isUnallocated() {
        return "unallocated".equalsIgnoreCase(value);
    }

    public static DomainName UNALLOCATED = DomainName.of("unallocated");

    public static DomainName of(String value) {
        return new DomainName(value);
    }
}
