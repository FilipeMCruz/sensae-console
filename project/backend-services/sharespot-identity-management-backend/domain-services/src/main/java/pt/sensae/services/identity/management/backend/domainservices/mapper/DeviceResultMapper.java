package pt.sensae.services.identity.management.backend.domainservices.mapper;

import pt.sensae.services.identity.management.backend.domain.identity.device.Device;
import pt.sensae.services.identity.management.backend.domainservices.model.device.DeviceDomainPermissionsResult;
import pt.sensae.services.identity.management.backend.domainservices.model.device.DeviceResult;

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
