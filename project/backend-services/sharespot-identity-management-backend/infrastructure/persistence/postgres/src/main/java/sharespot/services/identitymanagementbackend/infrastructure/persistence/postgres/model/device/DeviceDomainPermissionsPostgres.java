package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.device;

import javax.persistence.*;

@Entity(name = "device_permission")
public class DeviceDomainPermissionsPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    public String domainOid;

    @ManyToOne
    public DevicePostgres device;
}
