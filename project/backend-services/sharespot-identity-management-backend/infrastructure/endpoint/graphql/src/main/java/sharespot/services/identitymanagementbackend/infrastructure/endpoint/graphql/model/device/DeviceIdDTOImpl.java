package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device;

import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;

import java.util.List;

public class DeviceIdDTOImpl implements DeviceIdDTO {
    public String oid;
    public List<DeviceDomainPermissionsDTOImpl> domains;
}
