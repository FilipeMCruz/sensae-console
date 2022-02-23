package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper;

import sharespot.services.devicerecordsbackend.domain.model.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.permissions.DevicePermissions;
import sharespot.services.devicerecordsbackend.domain.model.permissions.DomainId;
import sharespot.services.devicerecordsbackend.domain.model.permissions.Permissions;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions.DevicePermissionEntryPostgres;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions.DevicePermissionEntryTypePostgres;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions.DevicePermissionsPostgres;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PermissionMapper {

    public static DevicePermissionsPostgres domainToPostgres(DevicePermissions permissions) {
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

    public static DevicePermissions postgresToDomain(DevicePermissionsPostgres postgres) {
        var collect = postgres.entries
                .stream()
                .collect(Collectors.partitioningBy(e -> e.type.equals(DevicePermissionEntryTypePostgres.readWrite())));

        var permissions = new Permissions(
                collect.get(false).stream().map(e -> new DomainId(UUID.fromString(e.domainId))).collect(Collectors.toSet()),
                collect.get(true).stream().map(e -> new DomainId(UUID.fromString(e.domainId))).collect(Collectors.toSet()));

        return new DevicePermissions(new DeviceId(UUID.fromString(postgres.deviceId)), permissions);
    }
}
