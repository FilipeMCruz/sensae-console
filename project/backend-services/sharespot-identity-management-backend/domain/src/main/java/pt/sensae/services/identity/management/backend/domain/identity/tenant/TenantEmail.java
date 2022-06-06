package pt.sensae.services.identity.management.backend.domain.identity.tenant;

public record TenantEmail(String value) {
    public static TenantEmail of(String value) {
        return new TenantEmail(value);
    }

    public static TenantEmail empty() {
        return new TenantEmail("");
    }
}
