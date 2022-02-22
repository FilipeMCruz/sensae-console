package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device;

import sharespot.services.identitymanagementbackend.application.model.device.DeviceDTO;

import java.util.List;

public class DeviceDTOImpl implements DeviceDTO {
    public String oid;
    public List<DeviceDomainPermissionsDTOImpl> domains;
}
