package sharespot.services.identitymanagementbackend.domain.identity.permissions;

import java.util.Set;

public enum PermissionType {

    READ_DEVICE_INFORMATION,
    DELETE_DEVICE_INFORMATION,
    EDIT_DEVICE_INFORMATION,

    READ_DATA_TRANSFORMATION,
    DELETE_DATA_TRANSFORMATION,
    EDIT_DATA_TRANSFORMATION,

    READ_DATA_DECODER,
    EDIT_DATA_DECODER,
    DELETE_DATA_DECODER,

    EDIT_DOMAIN,
    CREATE_DOMAIN,
    READ_DOMAIN,

    EDIT_DEVICE,
    READ_DEVICE,

    EDIT_TENANT,
    READ_TENANT,

    READ_LIVE_DATA_FLEET_MANAGEMENT,
    READ_LATEST_DATA_FLEET_MANAGEMENT,
    READ_PAST_DATA_FLEET_MANAGEMENT,

    CREATE_GARDEN_SMART_IRRIGATION,
    EDIT_GARDEN_SMART_IRRIGATION,
    DELETE_GARDEN_SMART_IRRIGATION,
    READ_GARDEN_SMART_IRRIGATION,

    READ_LIVE_DATA_SMART_IRRIGATION,
    READ_LATEST_DATA_SMART_IRRIGATION,
    READ_PAST_DATA_SMART_IRRIGATION,

    CONTROL_VALVE_SMART_IRRIGATION,

    READ_RULE_SCENARIO,

    DELETE_RULE_SCENARIO,

    EDIT_RULE_SCENARIO;

    public static void reviewPermissions(Set<PermissionType> permissions) {

        reviewDeviceRecordsPermissions(permissions);

        reviewDataDecoderPermissions(permissions);

        reviewDataTransformationPermissions(permissions);

        reviewRuleManagementPermissions(permissions);

        reviewIdentityManagementDevicePermissions(permissions);

        reviewIdentityManagementDomainPermissions(permissions);

        reviewIdentityManagementTenantPermissions(permissions);

        reviewGardenSmartIrrigationPermissions(permissions);

        reviewDataFleetManagementPermissions(permissions);
    }

    private static void reviewIdentityManagementTenantPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.EDIT_TENANT)) {
            permissions.add(PermissionType.READ_TENANT);
        }
    }

    private static void reviewDataFleetManagementPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.READ_PAST_DATA_FLEET_MANAGEMENT)) {
            permissions.add(PermissionType.READ_LIVE_DATA_FLEET_MANAGEMENT);
            permissions.add(PermissionType.READ_LATEST_DATA_FLEET_MANAGEMENT);
        }
        if (permissions.contains(PermissionType.READ_LIVE_DATA_FLEET_MANAGEMENT)) {
            permissions.add(PermissionType.READ_LATEST_DATA_FLEET_MANAGEMENT);
        }
    }

    private static void reviewIdentityManagementDomainPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.EDIT_DOMAIN)) {
            permissions.add(PermissionType.CREATE_DOMAIN);
        }

        if (permissions.contains(PermissionType.EDIT_DOMAIN)) {
            permissions.add(PermissionType.READ_DOMAIN);
        }
    }

    private static void reviewIdentityManagementDevicePermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.EDIT_DEVICE)) {
            permissions.add(PermissionType.READ_DEVICE);
        }
    }

    private static void reviewDeviceRecordsPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.DELETE_DEVICE_INFORMATION)) {
            permissions.add(PermissionType.EDIT_DEVICE_INFORMATION);
        }

        if (permissions.contains(PermissionType.EDIT_DEVICE_INFORMATION)) {
            permissions.add(PermissionType.READ_DEVICE_INFORMATION);
        }
    }

    private static void reviewDataDecoderPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.DELETE_DATA_DECODER)) {
            permissions.add(PermissionType.EDIT_DATA_DECODER);
        }

        if (permissions.contains(PermissionType.EDIT_DATA_DECODER)) {
            permissions.add(PermissionType.READ_DATA_DECODER);
        }
    }

    private static void reviewDataTransformationPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.DELETE_DATA_TRANSFORMATION)) {
            permissions.add(PermissionType.EDIT_DATA_TRANSFORMATION);
        }

        if (permissions.contains(PermissionType.EDIT_DATA_TRANSFORMATION)) {
            permissions.add(PermissionType.READ_DATA_TRANSFORMATION);
        }
    }

    private static void reviewRuleManagementPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.DELETE_RULE_SCENARIO)) {
            permissions.add(PermissionType.EDIT_RULE_SCENARIO);
        }

        if (permissions.contains(PermissionType.EDIT_RULE_SCENARIO)) {
            permissions.add(PermissionType.READ_RULE_SCENARIO);
        }
    }

    private static void reviewGardenSmartIrrigationPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.DELETE_GARDEN_SMART_IRRIGATION)) {
            permissions.add(PermissionType.EDIT_GARDEN_SMART_IRRIGATION);
        }

        if (permissions.contains(PermissionType.EDIT_GARDEN_SMART_IRRIGATION)) {
            permissions.add(PermissionType.CREATE_GARDEN_SMART_IRRIGATION);
        }

        if (permissions.contains(PermissionType.CREATE_GARDEN_SMART_IRRIGATION)) {
            permissions.add(PermissionType.READ_GARDEN_SMART_IRRIGATION);
        }

        reviewDataSmartIrrigationPermissions(permissions);
    }

    private static void reviewDataSmartIrrigationPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.READ_PAST_DATA_SMART_IRRIGATION)) {
            permissions.add(PermissionType.READ_LIVE_DATA_SMART_IRRIGATION);
            permissions.add(PermissionType.READ_LATEST_DATA_SMART_IRRIGATION);
        }

        if (permissions.contains(PermissionType.READ_LIVE_DATA_SMART_IRRIGATION)) {
            permissions.add(PermissionType.READ_GARDEN_SMART_IRRIGATION);
            permissions.add(PermissionType.READ_LATEST_DATA_SMART_IRRIGATION);
        }

        reviewControlSmartIrrigationPermissions(permissions);
    }

    private static void reviewControlSmartIrrigationPermissions(Set<PermissionType> permissions) {
        if (permissions.contains(PermissionType.CONTROL_VALVE_SMART_IRRIGATION)) {
            permissions.add(PermissionType.READ_LIVE_DATA_SMART_IRRIGATION);
            permissions.add(PermissionType.READ_LATEST_DATA_SMART_IRRIGATION);
            permissions.add(PermissionType.READ_GARDEN_SMART_IRRIGATION);
        }
    }
}
