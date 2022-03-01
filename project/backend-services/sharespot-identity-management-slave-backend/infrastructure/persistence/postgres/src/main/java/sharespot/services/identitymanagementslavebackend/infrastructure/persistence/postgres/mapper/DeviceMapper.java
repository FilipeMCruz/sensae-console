package sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.mapper;


import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.Device;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceDomainPermissions;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DevicePermissions;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.model.device.DeviceDomainPermissionsPostgres;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.model.device.DevicePermissionsPostgres;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.model.device.DevicePostgres;

import java.util.UUID;
import java.util.stream.Collectors;

public class DeviceMapper {

    public static DevicePostgres domainToPostgres(Device device) {
        var domainPostgres = new DevicePostgres();
        domainPostgres.oid = device.getOid().value().toString();
        domainPostgres.devicePermissions = device.getDomains().stream().map(p -> {
            var deviceDomainPermissionsPostgres = new DeviceDomainPermissionsPostgres();
            deviceDomainPermissionsPostgres.domainOid = p.domain().value().toString();
            deviceDomainPermissionsPostgres.permission = p.permissions().equals(DevicePermissions.READ) ?
                    DevicePermissionsPostgres.read() :
                    DevicePermissionsPostgres.writeRead();
            deviceDomainPermissionsPostgres.device = domainPostgres;
            return deviceDomainPermissionsPostgres;
        }).collect(Collectors.toSet());
        return domainPostgres;
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
                .toList();
        return new Device(oid, domains);
    }
}
