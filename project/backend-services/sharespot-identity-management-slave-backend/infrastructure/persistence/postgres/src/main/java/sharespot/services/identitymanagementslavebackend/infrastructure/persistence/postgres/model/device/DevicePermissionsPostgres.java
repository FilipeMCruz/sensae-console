package sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.model.device;

import javax.persistence.Embeddable;

@Embeddable
public class DevicePermissionsPostgres {
    /**
     * Postgres does not support enums, so to address this i've made my "enum"
     * <p>
     * READ -> 0
     * WRITE_READ -> 1
     */
    public int type;

    public static DevicePermissionsPostgres read() {
        var type = new DevicePermissionsPostgres();
        type.type = 0;
        return type;
    }

    public static DevicePermissionsPostgres writeRead() {
        var type = new DevicePermissionsPostgres();
        type.type = 1;
        return type;
    }
}
