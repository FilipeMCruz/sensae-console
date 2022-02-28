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
            case READ_DEVICE_RECORDS -> new DomainPermissionTypePostgres(1);
            case WRITE_DEVICE_RECORDS -> new DomainPermissionTypePostgres(2);
            case READ_DATA_TRANSFORMATIONS -> new DomainPermissionTypePostgres(3);
            case WRITE_DATA_TRANSFORMATIONS -> new DomainPermissionTypePostgres(4);
            case READ_FLEET_MANAGEMENT -> new DomainPermissionTypePostgres(5);
        };
    }

    public static PermissionType from(DomainPermissionTypePostgres postgres) {
        return switch (postgres.type) {
            case 1 -> PermissionType.READ_DEVICE_RECORDS;
            case 2 -> PermissionType.WRITE_DEVICE_RECORDS;
            case 3 -> PermissionType.READ_DATA_TRANSFORMATIONS;
            case 4 -> PermissionType.WRITE_DATA_TRANSFORMATIONS;
            case 5 -> PermissionType.READ_FLEET_MANAGEMENT;
            default -> throw new RuntimeException("Invalid Value");
        };
    }
}
