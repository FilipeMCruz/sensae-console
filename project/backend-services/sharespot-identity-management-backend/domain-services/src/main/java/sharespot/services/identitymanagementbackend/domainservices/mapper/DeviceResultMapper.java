package sharespot.services.identitymanagementbackend.domainservices.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.device.Device;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceDomainPermissionsResult;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceResult;

public class DeviceResultMapper {

    public static DeviceResult toResult(Device device) {
        var deviceResult = new DeviceResult();
        deviceResult.oid = device.oid().value().toString();
        deviceResult.domains = device.domains()
                .stream()
                .map(d -> {
                    var deviceDomainPermissions = new DeviceDomainPermissionsResult();
                    deviceDomainPermissions.oid = d.value().toString();
                    return deviceDomainPermissions;
                }).toList();
        return deviceResult;
    }
}
