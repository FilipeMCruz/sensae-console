package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeConfigUpdateCommandDTO;

import java.util.Set;

public class AddresseeConfigUpdateCommandDTOImpl implements AddresseeConfigUpdateCommandDTO {

    public Set<AddresseeConfigDTOImpl> entries;

}
