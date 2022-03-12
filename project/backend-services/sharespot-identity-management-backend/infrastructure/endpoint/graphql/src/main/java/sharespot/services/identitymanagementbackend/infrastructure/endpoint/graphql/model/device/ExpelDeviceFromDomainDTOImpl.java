package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device;

import sharespot.services.identitymanagementbackend.application.model.device.ExpelDeviceFromDomainDTO;

public class ExpelDeviceFromDomainDTOImpl implements ExpelDeviceFromDomainDTO {
    public String deviceOid;
    public String domainOid;
}
