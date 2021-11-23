package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model;

import javax.persistence.*;

@Entity(name = "record_entry")
public class DeviceRecordEntryPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    public String label;

    public String content;

    @Embedded
    public DeviceRecordEntryTypePostgres type;
}
