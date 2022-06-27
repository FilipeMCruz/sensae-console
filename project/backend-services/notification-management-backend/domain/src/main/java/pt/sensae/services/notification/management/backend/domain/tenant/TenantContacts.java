package pt.sensae.services.notification.management.backend.domain.tenant;

public record TenantContacts(String email, String phone) {
    public static TenantContacts of(String email, String phone) {
        return new TenantContacts(email, phone);
    }

}
