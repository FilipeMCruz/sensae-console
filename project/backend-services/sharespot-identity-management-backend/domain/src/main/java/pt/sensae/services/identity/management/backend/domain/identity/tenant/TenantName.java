package pt.sensae.services.identity.management.backend.domain.identity.tenant;

public record TenantName(String value) {

    public static TenantName of(String value) {
        return new TenantName(value);
    }

    public boolean isAnonymous() {
        return this.value.equalsIgnoreCase("Anonymous");
    }
}
