package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions;

import javax.persistence.*;

@Entity(name = "permission_entry")
public class DevicePermissionEntryPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    public String domainId;

    @Embedded
    public DevicePermissionEntryTypePostgres type;
}
