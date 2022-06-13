package pt.sensae.services.device.ownership.backend.domain.domain;

public record DomainName(String value) {

    public boolean isUnallocated() {
        return "unallocated".equalsIgnoreCase(value);
    }

    public static DomainName UNALLOCATED = DomainName.of("unallocated");

    public static DomainName of(String value) {
        return new DomainName(value);
    }
}
