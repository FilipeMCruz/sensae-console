package pt.sensae.services.notification.management.backend.domain.adressee;

public record AddresseeContacts(String email, String phone) {
    public static AddresseeContacts of(String email, String phone) {
        return new AddresseeContacts(email, phone);
    }

    public static AddresseeContacts empty() {
        return new AddresseeContacts("", "");
    }
}
