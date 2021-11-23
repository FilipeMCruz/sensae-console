package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "record")
public class DeviceRecordsPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String deviceId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DeviceRecordEntryPostgres> entries;
}
