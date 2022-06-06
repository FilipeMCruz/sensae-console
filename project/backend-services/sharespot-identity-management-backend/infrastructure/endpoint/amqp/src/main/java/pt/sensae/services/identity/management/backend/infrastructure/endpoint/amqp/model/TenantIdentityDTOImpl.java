package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model;

import pt.sensae.services.identity.management.backend.application.internal.tenant.TenantIdentityDTO;

import java.util.Set;
import java.util.UUID;

public class TenantIdentityDTOImpl implements TenantIdentityDTO {

    public UUID id;

    public Set<UUID> domains;

    public String name;

    public String email;

    public String phoneNumber;
}
