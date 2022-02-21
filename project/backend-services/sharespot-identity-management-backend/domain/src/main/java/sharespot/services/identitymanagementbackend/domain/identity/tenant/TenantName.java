package sharespot.services.identitymanagementbackend.domain.identity.tenant;

public record TenantName(String value) {

    public static TenantName of(String value) {
        return new TenantName(value);
    }
}
