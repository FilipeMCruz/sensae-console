package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.domain;

import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;

import javax.persistence.Embeddable;

@Embeddable
public class DomainPermissionTypePostgres {
    /**
     * Postgres does not support enums, so to address this i've made my "enum"
     * <p>
     * 1 -> PermissionType.READ_DEVICE_RECORD;
     * 2 -> PermissionType.EDIT_DEVICE_RECORD;
     * 3 -> PermissionType.READ_DATA_TRANSFORMATION;
     * 4 -> PermissionType.EDIT_DATA_TRANSFORMATION;
     * 5 -> PermissionType.READ_LIVE_DATA_FLEET_MANAGEMENT;
     * 6 -> PermissionType.WRITE_DOMAIN;
     * 7 -> PermissionType.READ_DOMAIN;
     * 8 -> PermissionType.WRITE_DEVICE;
     * 9 -> PermissionType.READ_DEVICE;
     * 10 -> PermissionType.WRITE_TENANT;
     * 11 -> PermissionType.READ_TENANT;
     * 12 -> PermissionType.READ_DATA_DECODER;
     * 13 -> PermissionType.EDIT_DATA_DECODER;
     * 14 -> PermissionType.CREATE_DEVICE_RECORD;
     * 15 -> PermissionType.DELETE_DEVICE_RECORD;
     * 16 -> PermissionType.CREATE_DATA_TRANSFORMATION;
     * 17 -> PermissionType.DELETE_DATA_TRANSFORMATION;
     * 18 -> PermissionType.CREATE_DATA_DECODER;
     * 19 -> PermissionType.DELETE_DATA_DECODER;
     * 20 -> PermissionType.READ_PAST_DATA_FLEET_MANAGEMENT;
     * 21 -> PermissionType.CREATE_GARDEN_SMART_IRRIGATION;
     * 22 -> PermissionType.EDIT_GARDEN_SMART_IRRIGATION;
     * 23 -> PermissionType.DELETE_GARDEN_SMART_IRRIGATION;
     * 24 -> PermissionType.READ_GARDEN_SMART_IRRIGATION;
     * 25 -> PermissionType.READ_LIVE_DATA_SMART_IRRIGATION;
     * 26 -> PermissionType.READ_PAST_DATA_SMART_IRRIGATION;
     * 27 -> PermissionType.CONTROL_VALVE_SMART_IRRIGATION;
     */
    public int type;

    public DomainPermissionTypePostgres() {

    }

    private DomainPermissionTypePostgres(int type) {
        this.type = type;
    }

    public static DomainPermissionTypePostgres create(PermissionType type) {
        return switch (type) {
            case READ_DEVICE_INFORMATION -> new DomainPermissionTypePostgres(1);
            case DELETE_DEVICE_INFORMATION -> new DomainPermissionTypePostgres(15);
            case EDIT_DEVICE_INFORMATION -> new DomainPermissionTypePostgres(2);
            case READ_DATA_TRANSFORMATION -> new DomainPermissionTypePostgres(3);
            case DELETE_DATA_TRANSFORMATION -> new DomainPermissionTypePostgres(17);
            case EDIT_DATA_TRANSFORMATION -> new DomainPermissionTypePostgres(4);
            case READ_DATA_DECODER -> new DomainPermissionTypePostgres(12);
            case EDIT_DATA_DECODER -> new DomainPermissionTypePostgres(13);
            case DELETE_DATA_DECODER -> new DomainPermissionTypePostgres(19);
            case READ_LIVE_DATA_FLEET_MANAGEMENT -> new DomainPermissionTypePostgres(5);
            case READ_LATEST_DATA_FLEET_MANAGEMENT -> new DomainPermissionTypePostgres(16);
            case READ_PAST_DATA_FLEET_MANAGEMENT -> new DomainPermissionTypePostgres(20);
            case EDIT_DOMAIN -> new DomainPermissionTypePostgres(6);
            case READ_DOMAIN -> new DomainPermissionTypePostgres(7);
            case EDIT_DEVICE -> new DomainPermissionTypePostgres(8);
            case READ_DEVICE -> new DomainPermissionTypePostgres(9);
            case EDIT_TENANT -> new DomainPermissionTypePostgres(10);
            case READ_TENANT -> new DomainPermissionTypePostgres(11);
            case CREATE_GARDEN_SMART_IRRIGATION -> new DomainPermissionTypePostgres(21);
            case EDIT_GARDEN_SMART_IRRIGATION -> new DomainPermissionTypePostgres(22);
            case DELETE_GARDEN_SMART_IRRIGATION -> new DomainPermissionTypePostgres(23);
            case READ_GARDEN_SMART_IRRIGATION -> new DomainPermissionTypePostgres(24);
            case READ_LIVE_DATA_SMART_IRRIGATION -> new DomainPermissionTypePostgres(25);
            case READ_LATEST_DATA_SMART_IRRIGATION -> new DomainPermissionTypePostgres(18);
            case READ_PAST_DATA_SMART_IRRIGATION -> new DomainPermissionTypePostgres(26);
            case CONTROL_VALVE_SMART_IRRIGATION -> new DomainPermissionTypePostgres(27);
            case CREATE_DOMAIN -> new DomainPermissionTypePostgres(14);
            case READ_RULE_SCENARIO -> new DomainPermissionTypePostgres(28);
            case EDIT_RULE_SCENARIO -> new DomainPermissionTypePostgres(29);
            case DELETE_RULE_SCENARIO -> new DomainPermissionTypePostgres(30);
        };
    }

    public static PermissionType from(DomainPermissionTypePostgres postgres) {
        return switch (postgres.type) {
            case 1 -> PermissionType.READ_DEVICE_INFORMATION;
            case 2 -> PermissionType.EDIT_DEVICE_INFORMATION;
            case 3 -> PermissionType.READ_DATA_TRANSFORMATION;
            case 4 -> PermissionType.EDIT_DATA_TRANSFORMATION;
            case 5 -> PermissionType.READ_LIVE_DATA_FLEET_MANAGEMENT;
            case 6 -> PermissionType.EDIT_DOMAIN;
            case 7 -> PermissionType.READ_DOMAIN;
            case 8 -> PermissionType.EDIT_DEVICE;
            case 9 -> PermissionType.READ_DEVICE;
            case 10 -> PermissionType.EDIT_TENANT;
            case 11 -> PermissionType.READ_TENANT;
            case 12 -> PermissionType.READ_DATA_DECODER;
            case 13 -> PermissionType.EDIT_DATA_DECODER;
            case 14 -> PermissionType.CREATE_DOMAIN;
            case 15 -> PermissionType.DELETE_DEVICE_INFORMATION;
            case 16 -> PermissionType.READ_LATEST_DATA_FLEET_MANAGEMENT;
            case 17 -> PermissionType.DELETE_DATA_TRANSFORMATION;
            case 18 -> PermissionType.READ_LATEST_DATA_SMART_IRRIGATION;
            case 19 -> PermissionType.DELETE_DATA_DECODER;
            case 20 -> PermissionType.READ_PAST_DATA_FLEET_MANAGEMENT;
            case 21 -> PermissionType.CREATE_GARDEN_SMART_IRRIGATION;
            case 22 -> PermissionType.EDIT_GARDEN_SMART_IRRIGATION;
            case 23 -> PermissionType.DELETE_GARDEN_SMART_IRRIGATION;
            case 24 -> PermissionType.READ_GARDEN_SMART_IRRIGATION;
            case 25 -> PermissionType.READ_LIVE_DATA_SMART_IRRIGATION;
            case 26 -> PermissionType.READ_PAST_DATA_SMART_IRRIGATION;
            case 27 -> PermissionType.CONTROL_VALVE_SMART_IRRIGATION;
            case 28 -> PermissionType.READ_RULE_SCENARIO;
            case 29 -> PermissionType.EDIT_RULE_SCENARIO;
            case 30 -> PermissionType.DELETE_RULE_SCENARIO;
            default -> throw new RuntimeException("Invalid Value");
        };
    }
}
