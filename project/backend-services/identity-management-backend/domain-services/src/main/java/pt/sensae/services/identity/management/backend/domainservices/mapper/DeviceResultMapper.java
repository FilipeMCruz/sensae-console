package pt.sensae.services.identity.management.backend.domainservices.mapper;

import pt.sensae.services.identity.management.backend.domain.device.DeviceInformation;
import pt.sensae.services.identity.management.backend.domain.identity.device.Device;
import pt.sensae.services.identity.management.backend.domainservices.model.device.DeviceDomainPermissionsResult;
import pt.sensae.services.identity.management.backend.domainservices.model.device.DeviceResult;

public class DeviceResultMapper {

    public static DeviceResult toResult(Device device, DeviceInformation information) {
        var deviceResult = new DeviceResult();
        deviceResult.oid = device.oid().value().toString();
        deviceResult.name = information != null ? information.name().value() : "";
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
