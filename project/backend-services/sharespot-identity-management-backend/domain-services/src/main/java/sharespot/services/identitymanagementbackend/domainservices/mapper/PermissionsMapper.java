package sharespot.services.identitymanagementbackend.domainservices.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;

import java.util.stream.Stream;

public class PermissionsMapper {

    public static Stream<String> toResult(Stream<PermissionType> permissions) {
        return permissions.distinct().map(p ->
                switch (p) {
                    case READ_DEVICE_RECORDS -> "device_records:records:read";
                    case WRITE_DEVICE_RECORDS -> "device_records:records:write";
                    case READ_DATA_TRANSFORMATIONS -> "data_transformations:transformations:read";
                    case WRITE_DATA_TRANSFORMATIONS -> "data_transformations:transformations:write";
                    case READ_FLEET_MANAGEMENT -> "fleet_management:read";
                    case WRITE_DOMAINS -> "identity_management:domains:create";
                    case READ_DOMAINS -> "identity_management:domains:read";
                    case READ_TENANT -> "identity_management:tenant:read";
                    case READ_DEVICE -> "identity_management:device:read";
                    case WRITE_TENANT -> "identity_management:tenant:write";
                    case WRITE_DEVICE -> "identity_management:device:write";
                });
    }

    public static Stream<PermissionType> toDomain(Stream<String> permissions) {
        return permissions.distinct().map(p ->
                switch (p) {
                    case "device_records:records:read" -> PermissionType.READ_DEVICE_RECORDS;
                    case "device_records:records:write" -> PermissionType.WRITE_DEVICE_RECORDS;
                    case "data_transformations:transformations:read" -> PermissionType.READ_DATA_TRANSFORMATIONS;
                    case "data_transformations:transformations:write" -> PermissionType.WRITE_DATA_TRANSFORMATIONS;
                    case "fleet_management:read" -> PermissionType.READ_FLEET_MANAGEMENT;
                    case "identity_management:domains:create" -> PermissionType.WRITE_DOMAINS;
                    case "identity_management:domains:read" -> PermissionType.READ_DOMAINS;
                    case "identity_management:tenant:read" -> PermissionType.READ_TENANT;
                    case "identity_management:device:read" -> PermissionType.READ_DEVICE;
                    case "identity_management:tenant:write" -> PermissionType.WRITE_TENANT;
                    case "identity_management:device:write" -> PermissionType.WRITE_DEVICE;
                    default -> throw new RuntimeException("Invalid Permissions");
                });
    }
}
