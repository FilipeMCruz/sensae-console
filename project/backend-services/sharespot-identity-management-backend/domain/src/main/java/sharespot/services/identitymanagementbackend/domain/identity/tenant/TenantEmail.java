package sharespot.services.identitymanagementbackend.domain.identity.tenant;

public record TenantEmail(String value) {
    public static TenantEmail of(String value) {
        return new TenantEmail(value);
    }
}
