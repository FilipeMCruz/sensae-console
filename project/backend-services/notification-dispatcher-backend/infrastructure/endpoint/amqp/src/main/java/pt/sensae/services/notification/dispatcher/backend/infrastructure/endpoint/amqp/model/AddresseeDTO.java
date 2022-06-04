package pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.model;

import java.util.Set;

public class AddresseeDTO {
    
    public String id;

    public Set<AddresseeConfigDTO> entries;
}
