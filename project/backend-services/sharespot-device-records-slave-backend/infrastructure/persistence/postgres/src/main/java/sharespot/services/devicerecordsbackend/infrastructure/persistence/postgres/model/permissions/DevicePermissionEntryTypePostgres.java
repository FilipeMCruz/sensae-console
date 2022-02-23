package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions;

import javax.persistence.Embeddable;

@Embeddable
public class DevicePermissionEntryTypePostgres {

    /**
     * Postgres does not support enums, so to address this i've made my "enum"
     * <p>
     * read permission -> 0
     * read and write permission -> 1
     */
    public int type;

    public static DevicePermissionEntryTypePostgres read() {
        var type = new DevicePermissionEntryTypePostgres();
        type.type = 0;
        return type;
    }

    public static DevicePermissionEntryTypePostgres readWrite() {
        var type = new DevicePermissionEntryTypePostgres();
        type.type = 1;
        return type;
    }
}
