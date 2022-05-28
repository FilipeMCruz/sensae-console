package sharespot.services.identitymanagementbackend.application.internal.tenant;

import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;

import java.util.stream.Stream;

public interface TenantIdentitySyncHandler {

    void publishState(Stream<Tenant> tenants);
}
