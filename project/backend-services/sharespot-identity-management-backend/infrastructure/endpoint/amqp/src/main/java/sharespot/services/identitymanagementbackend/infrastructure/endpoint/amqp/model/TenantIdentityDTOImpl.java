package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model;

import sharespot.services.identitymanagementbackend.application.internal.tenant.TenantIdentityDTO;

import java.util.Set;
import java.util.UUID;

public class TenantIdentityDTOImpl implements TenantIdentityDTO {

    public UUID id;

    public Set<UUID> domains;

    public String name;

    public String email;

    public String phoneNumber;
}
