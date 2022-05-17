package sharespot.services.identitymanagementbackend.domainservices.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.device.Device;
import sharespot.services.identitymanagementbackend.domain.identity.device.DevicePermissions;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceDomainPermissionsResult;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DevicePermissionsResult;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceResult;

public class DeviceResultMapper {

    public static DeviceResult toResult(Device device) {
        var deviceResult = new DeviceResult();
        deviceResult.oid = device.getOid().value().toString();
        deviceResult.domains = device.getDomains()
                .stream()
                .map(d -> {
                    var deviceDomainPermissions = new DeviceDomainPermissionsResult();
                    deviceDomainPermissions.oid = d.domain().value().toString();
                    return deviceDomainPermissions;
                }).toList();
        return deviceResult;
    }
}
