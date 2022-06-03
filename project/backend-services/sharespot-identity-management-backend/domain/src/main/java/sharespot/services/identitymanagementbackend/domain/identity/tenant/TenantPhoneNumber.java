package sharespot.services.identitymanagementbackend.domain.identity.tenant;

public record TenantPhoneNumber(String value) {
    public static TenantPhoneNumber of(String value) {
        return new TenantPhoneNumber(value);
    }

    public static TenantPhoneNumber empty() {
        return TenantPhoneNumber.of("");
    }
}
