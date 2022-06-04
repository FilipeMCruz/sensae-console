package pt.sensae.services.notification.dispatcher.backend.domain.tenant;

public record TenantName(String value) {
    public static TenantName of(String value) {
        return new TenantName(value);
    }

    public static TenantName empty() {
        return new TenantName("");
    }
}
