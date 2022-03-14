package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.device.Device;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceDomainPermissions;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.domain.identity.device.DevicePermissions;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.device.DeviceDomainPermissionsPostgres;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.device.DevicePermissionsPostgres;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.device.DevicePostgres;

import java.util.UUID;
import java.util.stream.Collectors;

public class DeviceMapper {

    public static DevicePostgres domainToPostgres(Device device) {
        var devicePostgres = new DevicePostgres();
        devicePostgres.oid = device.getOid().value().toString();
        devicePostgres.devicePermissions = device.getDomains().stream().map(p -> {
            var deviceDomainPermissionsPostgres = new DeviceDomainPermissionsPostgres();
            deviceDomainPermissionsPostgres.domainOid = p.domain().value().toString();
            deviceDomainPermissionsPostgres.permission = p.permissions().equals(DevicePermissions.READ) ?
                    DevicePermissionsPostgres.read() :
                    DevicePermissionsPostgres.writeRead();
            deviceDomainPermissionsPostgres.device = devicePostgres;
            return deviceDomainPermissionsPostgres;
        }).collect(Collectors.toSet());
        return devicePostgres;
    }

    public static Device postgresToDomain(DevicePostgres postgres) {
        var oid = DeviceId.of(UUID.fromString(postgres.oid));
        var domains = postgres.devicePermissions
                .stream()
                .map(d -> new DeviceDomainPermissions(
                        DomainId.of(UUID.fromString(d.domainOid)),
                        d.permission.type == 0 ?
                                DevicePermissions.READ :
                                DevicePermissions.READ_WRITE))
                .collect(Collectors.toList());
        return new Device(oid, domains);
    }
}
