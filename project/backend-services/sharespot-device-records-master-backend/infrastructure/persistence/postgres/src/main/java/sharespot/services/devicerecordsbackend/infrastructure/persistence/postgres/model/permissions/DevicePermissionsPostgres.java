package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "permission")
public class DevicePermissionsPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String deviceId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DevicePermissionEntryPostgres> entries;
}
