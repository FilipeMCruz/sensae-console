package sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.model.device;

import javax.persistence.*;

@Entity(name = "device_permission")
public class DeviceDomainPermissionsPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    public String domainOid;

    @Embedded
    public DevicePermissionsPostgres permission;

    @ManyToOne
    public DevicePostgres device;
}
