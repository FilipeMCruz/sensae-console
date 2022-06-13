package pt.sensae.services.notification.dispatcher.backend.domain.tenant;

public record TenantContacts(String email, String phone) {
    public static TenantContacts of(String email, String phone) {
        return new TenantContacts(email, phone);
    }

    public static TenantContacts empty() {
        return new TenantContacts("", "");
    }
}
