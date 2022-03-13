package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.domain;

import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;

import javax.persistence.Embeddable;

@Embeddable
public class DomainPermissionTypePostgres {
    /**
     * Postgres does not support enums, so to address this i've made my "enum"
     * <p>
     * READ_DEVICE_RECORDS -> 0
     * WRITE_DEVICE_RECORDS -> 1
     * READ_DATA_TRANSFORMATIONS -> 2
     * WRITE_DATA_TRANSFORMATIONS -> 3
     * ACCESS_FLEET_MANAGEMENT -> 4
     */
    public int type;

    public DomainPermissionTypePostgres() {

    }

    private DomainPermissionTypePostgres(int type) {
        this.type = type;
    }

    public static DomainPermissionTypePostgres create(PermissionType type) {
        return switch (type) {
            case READ_DEVICE_RECORD -> new DomainPermissionTypePostgres(1);
            case WRITE_DEVICE_RECORD -> new DomainPermissionTypePostgres(2);
            case READ_DATA_TRANSFORMATION -> new DomainPermissionTypePostgres(3);
            case WRITE_DATA_TRANSFORMATION -> new DomainPermissionTypePostgres(4);
            case READ_FLEET_MANAGEMENT -> new DomainPermissionTypePostgres(5);
            case WRITE_DOMAIN -> new DomainPermissionTypePostgres(6);
            case READ_DOMAIN -> new DomainPermissionTypePostgres(7);
            case WRITE_DEVICE -> new DomainPermissionTypePostgres(8);
            case READ_DEVICE -> new DomainPermissionTypePostgres(9);
            case WRITE_TENANT -> new DomainPermissionTypePostgres(10);
            case READ_TENANT -> new DomainPermissionTypePostgres(11);
        };
    }

    public static PermissionType from(DomainPermissionTypePostgres postgres) {
        return switch (postgres.type) {
            case 1 -> PermissionType.READ_DEVICE_RECORD;
            case 2 -> PermissionType.WRITE_DEVICE_RECORD;
            case 3 -> PermissionType.READ_DATA_TRANSFORMATION;
            case 4 -> PermissionType.WRITE_DATA_TRANSFORMATION;
            case 5 -> PermissionType.READ_FLEET_MANAGEMENT;
            case 6 -> PermissionType.WRITE_DOMAIN;
            case 7 -> PermissionType.READ_DOMAIN;
            case 8 -> PermissionType.WRITE_DEVICE;
            case 9 -> PermissionType.READ_DEVICE;
            case 10 -> PermissionType.WRITE_TENANT;
            case 11 -> PermissionType.READ_TENANT;
            default -> throw new RuntimeException("Invalid Value");
        };
    }
}
