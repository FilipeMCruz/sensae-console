package pt.sensae.services.identity.management.backend.application.internal.tenant;

import java.util.stream.Stream;

public interface TenantIdentitySyncHandler {

    void publishState(Stream<TenantIdentityDTO> tenants);
}
