package sharespot.services.identitymanagementbackend.domainservices.model.device;

import sharespot.services.identitymanagementbackend.domain.identity.device.Device;
import sharespot.services.identitymanagementbackend.domain.identity.device.DevicePermissions;

public class DeviceResultMapper {

    public static DeviceResult toResult(Device device) {
        var deviceResult = new DeviceResult();
        deviceResult.oid = device.getOid().value().toString();
        deviceResult.domains = device.getDomains()
                .stream()
                .map(d -> {
                    var deviceDomainPermissions = new DeviceDomainPermissionsResult();
                    deviceDomainPermissions.oid = d.domain().value().toString();
                    deviceDomainPermissions.permissions = d.permissions().equals(DevicePermissions.READ) ?
                            DevicePermissionsResult.READ : DevicePermissionsResult.READ_WRITE;
                    return deviceDomainPermissions;
                }).toList();
        return deviceResult;
    }
}
