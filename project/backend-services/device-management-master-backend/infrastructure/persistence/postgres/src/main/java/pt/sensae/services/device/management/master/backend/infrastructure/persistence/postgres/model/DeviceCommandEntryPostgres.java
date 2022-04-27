package pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.model;

import javax.persistence.*;

@Entity(name = "command_entry")
public class DeviceCommandEntryPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    public String id;

    public String name;

    public String payload;

    public Integer port;

    public Integer subDeviceRef;

    @ManyToOne
    public DeviceInformationPostgres device;

}
