package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.model.device;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "device")
public class DevicePostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String oid;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "device", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DeviceDomainPermissionsPostgres> devicePermissions;
}
