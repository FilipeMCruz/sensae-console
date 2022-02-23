package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper;

import sharespot.services.devicerecordsbackend.domain.model.permissions.DevicePermissions;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions.DevicePermissionEntryPostgres;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions.DevicePermissionEntryTypePostgres;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions.DevicePermissionsPostgres;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PermissionMapper {

    public static DevicePermissionsPostgres postgresToDomain(DevicePermissions permissions) {
        var devicePermissionsPostgres = new DevicePermissionsPostgres();
        devicePermissionsPostgres.deviceId = permissions.device().toString();

        var readDomains = permissions.permissions().read().stream().map(p -> {
            var devicePermissionEntryPostgres = new DevicePermissionEntryPostgres();
            devicePermissionEntryPostgres.domainId = p.value().toString();
            devicePermissionEntryPostgres.type = DevicePermissionEntryTypePostgres.read();
            return devicePermissionEntryPostgres;
        }).toList();
        var writeDomains = permissions.permissions().readAndWrite().stream().map(p -> {
            var devicePermissionEntryPostgres = new DevicePermissionEntryPostgres();
            devicePermissionEntryPostgres.domainId = p.value().toString();
            devicePermissionEntryPostgres.type = DevicePermissionEntryTypePostgres.readWrite();
            return devicePermissionEntryPostgres;
        }).toList();

        devicePermissionsPostgres.entries = Stream.of(writeDomains, readDomains)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return devicePermissionsPostgres;
    }
}
