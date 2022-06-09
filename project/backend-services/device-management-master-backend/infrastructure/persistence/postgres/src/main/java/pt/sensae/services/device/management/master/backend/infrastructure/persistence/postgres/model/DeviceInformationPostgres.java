package pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    
    public String downlink;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "records", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DeviceRecordEntryPostgres> entries;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "controller", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DeviceSubSensorPostgres> subSensors;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DeviceCommandEntryPostgres> commands;
}
