package sharespot.services.identitymanagementbackend.application.internal.tenant;

import java.util.stream.Stream;

public interface TenantIdentitySyncHandler {

    void publishState(Stream<TenantIdentityDTO> tenants);
}
