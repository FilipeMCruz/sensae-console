package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.device;

import pt.sensae.services.identity.management.backend.application.model.device.DeviceIdDTO;

import java.util.List;

public class DeviceIdDTOImpl implements DeviceIdDTO {
    public String oid;
    public List<DeviceDomainPermissionsDTOImpl> domains;
}
