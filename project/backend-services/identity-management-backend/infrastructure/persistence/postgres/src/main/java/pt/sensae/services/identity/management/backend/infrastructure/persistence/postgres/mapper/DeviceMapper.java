package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.identity.management.backend.domain.identity.device.Device;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.model.device.DeviceDomainPermissionsPostgres;
import pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.model.device.DevicePostgres;

import java.util.UUID;
import java.util.stream.Collectors;

public class DeviceMapper {

    public static DevicePostgres domainToPostgres(Device device) {
        var devicePostgres = new DevicePostgres();
        devicePostgres.oid = device.oid().value().toString();
        devicePostgres.devicePermissions = device.domains().stream().map(p -> {
            var deviceDomainPermissionsPostgres = new DeviceDomainPermissionsPostgres();
            deviceDomainPermissionsPostgres.domainOid = p.value().toString();
            deviceDomainPermissionsPostgres.device = devicePostgres;
            return deviceDomainPermissionsPostgres;
        }).collect(Collectors.toSet());
        return devicePostgres;
    }

    public static Device postgresToDomain(DevicePostgres postgres) {
        var oid = DeviceId.of(UUID.fromString(postgres.oid));
        var domains = postgres.devicePermissions
                .stream()
                .map(d -> DomainId.of(UUID.fromString(d.domainOid)))
                .collect(Collectors.toSet());
        return new Device(oid, domains);
    }
}
