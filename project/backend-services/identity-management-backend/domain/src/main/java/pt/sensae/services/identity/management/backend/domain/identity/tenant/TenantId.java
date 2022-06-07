package pt.sensae.services.identity.management.backend.domain.identity.tenant;

import java.util.UUID;

public record TenantId(UUID value) {

    public static TenantId of(UUID value) {
        return new TenantId(value);
    }
}
