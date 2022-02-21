package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.device;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "device")
public class DevicePostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String oid;

    @OneToMany(mappedBy = "device", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DeviceDomainPermissionsPostgres> devicePermissions;
}
