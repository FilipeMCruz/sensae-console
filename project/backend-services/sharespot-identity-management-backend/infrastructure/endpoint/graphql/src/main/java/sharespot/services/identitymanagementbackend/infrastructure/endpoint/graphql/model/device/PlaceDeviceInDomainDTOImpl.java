package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device;

import sharespot.services.identitymanagementbackend.application.model.device.PlaceDeviceInDomainDTO;

public class PlaceDeviceInDomainDTOImpl implements PlaceDeviceInDomainDTO {
    public String deviceOid;
    public String domainOid;
    public boolean writePermission;
}
