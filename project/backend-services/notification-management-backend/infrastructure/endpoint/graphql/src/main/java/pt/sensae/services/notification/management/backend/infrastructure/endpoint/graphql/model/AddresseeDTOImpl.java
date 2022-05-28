package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;

import java.util.Set;

public class AddresseeDTOImpl implements AddresseeDTO {

    public Set<AddresseeConfigDTOImpl> entries;
}
