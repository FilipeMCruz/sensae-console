package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.device;

import pt.sensae.services.identity.management.backend.application.model.device.PlaceDeviceInDomainDTO;

public class PlaceDeviceInDomainDTOImpl implements PlaceDeviceInDomainDTO {
    public String deviceOid;
    public String domainOid;
}
