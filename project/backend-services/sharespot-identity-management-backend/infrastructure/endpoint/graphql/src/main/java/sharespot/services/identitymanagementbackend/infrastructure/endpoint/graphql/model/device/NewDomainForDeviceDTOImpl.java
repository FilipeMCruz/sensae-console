package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device;

import sharespot.services.identitymanagementbackend.application.model.device.NewDomainForDeviceDTO;

import java.util.UUID;

public class NewDomainForDeviceDTOImpl implements NewDomainForDeviceDTO {
    public UUID tenantOid;
    public UUID domainOid;
    public boolean writePermission;
}
