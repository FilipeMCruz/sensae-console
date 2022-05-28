package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model;

import java.util.Set;
import java.util.UUID;

public class TenantIdentityDTOImpl {

    public UUID id;

    public Set<UUID> domains;

    public String name;

    public String email;

    public String phoneNumber;
}
