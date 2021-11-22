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

    @OneToMany(mappedBy = "records", cascade = CascadeType.ALL)
    public Set<DeviceRecordEntryPostgres> entries;
}
