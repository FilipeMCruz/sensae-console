package pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "record")
public class DeviceInformationPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String deviceId;

    public String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "records", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DeviceRecordEntryPostgres> entries;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "controller", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DeviceSubSensorPostgres> subSensors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DeviceCommandEntryPostgres> commands;
}
