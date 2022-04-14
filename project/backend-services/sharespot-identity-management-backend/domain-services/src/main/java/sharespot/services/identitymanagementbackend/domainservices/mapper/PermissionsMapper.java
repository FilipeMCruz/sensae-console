package sharespot.services.identitymanagementbackend.domainservices.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;

import java.util.stream.Stream;

public class PermissionsMapper {

    public static Stream<String> toResult(Stream<PermissionType> permissions) {
        return permissions.distinct().map(p ->
                switch (p) {
                    case READ_DEVICE_RECORD -> "device_records:records:read";
                    case CREATE_DEVICE_RECORD -> "device_records:records:create";
                    case EDIT_DEVICE_RECORD -> "device_records:records:edit";
                    case DELETE_DEVICE_RECORD -> "device_records:records:delete";

                    case READ_DATA_TRANSFORMATION -> "data_transformations:transformations:read";
                    case CREATE_DATA_TRANSFORMATION -> "data_transformations:transformations:create";
                    case EDIT_DATA_TRANSFORMATION -> "data_transformations:transformations:edit";
                    case DELETE_DATA_TRANSFORMATION -> "data_transformations:transformations:delete";

                    case READ_DATA_DECODER -> "data_decoders:decoders:read";
                    case CREATE_DATA_DECODER -> "data_decoders:decoders:create";
                    case EDIT_DATA_DECODER -> "data_decoders:decoders:edit";
                    case DELETE_DATA_DECODER -> "data_decoders:decoders:delete";

                    case EDIT_DOMAIN -> "identity_management:domains:edit";
                    case CREATE_DOMAIN -> "identity_management:domains:create";
                    case READ_DOMAIN -> "identity_management:domains:read";

                    case EDIT_DEVICE -> "identity_management:device:write";
                    case READ_DEVICE -> "identity_management:device:read";

                    case EDIT_TENANT -> "identity_management:tenant:write";
                    case READ_TENANT -> "identity_management:tenant:read";

                    case READ_LIVE_DATA_FLEET_MANAGEMENT -> "fleet_management:live_data:read";
                    case READ_PAST_DATA_FLEET_MANAGEMENT -> "fleet_management:past_data:read";

                    case CREATE_GARDEN_SMART_IRRIGATION -> "smart_irrigation:garden:create";
                    case EDIT_GARDEN_SMART_IRRIGATION -> "smart_irrigation:garden:edit";    
                    case DELETE_GARDEN_SMART_IRRIGATION -> "smart_irrigation:garden:delete";
                    case READ_GARDEN_SMART_IRRIGATION -> "smart_irrigation:garden:read";

                    case READ_LIVE_DATA_SMART_IRRIGATION -> "smart_irrigation:live_data:read";
                    case READ_PAST_DATA_SMART_IRRIGATION -> "smart_irrigation:past_data:read";

                    case CONTROL_VALVE_SMART_IRRIGATION -> "smart_irrigation:valve:control";
                });
    }

    public static Stream<PermissionType> toDomain(Stream<String> permissions) {
        return permissions.distinct().map(p ->
                switch (p) {
                    case "device_records:records:read" -> PermissionType.READ_DEVICE_RECORD;
                    case "device_records:records:create" -> PermissionType.CREATE_DEVICE_RECORD;
                    case "device_records:records:edit" -> PermissionType.EDIT_DEVICE_RECORD;
                    case "device_records:records:delete" -> PermissionType.DELETE_DEVICE_RECORD;

                    case "data_transformations:transformations:read" -> PermissionType.READ_DATA_TRANSFORMATION;
                    case "data_transformations:transformations:create" -> PermissionType.CREATE_DATA_TRANSFORMATION;
                    case "data_transformations:transformations:edit" -> PermissionType.EDIT_DATA_TRANSFORMATION;
                    case "data_transformations:transformations:delete" -> PermissionType.DELETE_DATA_TRANSFORMATION;

                    case "data_decoders:decoders:read" -> PermissionType.READ_DATA_DECODER;
                    case "data_decoders:decoders:create" -> PermissionType.CREATE_DATA_DECODER;
                    case "data_decoders:decoders:edit" -> PermissionType.EDIT_DATA_DECODER;
                    case "data_decoders:decoders:delete" -> PermissionType.DELETE_DATA_DECODER;

                    case "identity_management:domains:create" -> PermissionType.CREATE_DOMAIN;
                    case "identity_management:domains:edit" -> PermissionType.EDIT_DOMAIN;
                    case "identity_management:domains:read" -> PermissionType.READ_DOMAIN;

                    case "identity_management:device:write" -> PermissionType.EDIT_DEVICE;
                    case "identity_management:device:read" -> PermissionType.READ_DEVICE;

                    case "identity_management:tenant:write" -> PermissionType.EDIT_TENANT;
                    case "identity_management:tenant:read" -> PermissionType.READ_TENANT;

                    case "fleet_management:live_data:read" -> PermissionType.READ_LIVE_DATA_FLEET_MANAGEMENT;
                    case "fleet_management:past_data:read" -> PermissionType.READ_PAST_DATA_FLEET_MANAGEMENT;

                    case "smart_irrigation:garden:create" -> PermissionType.CREATE_GARDEN_SMART_IRRIGATION;
                    case "smart_irrigation:garden:edit" -> PermissionType.EDIT_GARDEN_SMART_IRRIGATION;
                    case "smart_irrigation:garden:delete" -> PermissionType.DELETE_GARDEN_SMART_IRRIGATION;
                    case "smart_irrigation:garden:read" -> PermissionType.READ_GARDEN_SMART_IRRIGATION;

                    case "smart_irrigation:live_data:read" -> PermissionType.READ_LIVE_DATA_SMART_IRRIGATION;
                    case "smart_irrigation:past_data:read" -> PermissionType.READ_PAST_DATA_SMART_IRRIGATION;

                    case "smart_irrigation:valve:control" -> PermissionType.CONTROL_VALVE_SMART_IRRIGATION;
                    default -> throw new RuntimeException("Invalid Permissions");
                });
    }
}
