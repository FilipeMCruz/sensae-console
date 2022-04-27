package pt.sensae.services.device.commander.backend.infrastructure.persistence.postgres.model;

import javax.persistence.*;

@Entity(name = "record_entry")
public class DeviceRecordEntryPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    public String label;

    public String content;

    @ManyToOne
    public DeviceInformationPostgres records;

    @Embedded
    public DeviceRecordEntryTypePostgres type;
}
