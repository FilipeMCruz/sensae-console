package pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.model;

import java.util.Set;
import java.util.UUID;

public class TenantIdentityDTO {

    public UUID id;

    public Set<UUID> domains;

    public String name;

    public String email;

    public String phoneNumber;
}
