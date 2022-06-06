package pt.sensae.services.identity.management.backend.domain.identity.tenant;

import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;

public record TenantPhoneNumber(String value) {
    public static TenantPhoneNumber of(String value) {
        if (!value.matches("\\+\\d{6,}")) {
            throw new NotValidException("Invalid Phone Number");
        }
        return new TenantPhoneNumber(value);
    }

    public static TenantPhoneNumber empty() {
        return new TenantPhoneNumber("");
    }
}
