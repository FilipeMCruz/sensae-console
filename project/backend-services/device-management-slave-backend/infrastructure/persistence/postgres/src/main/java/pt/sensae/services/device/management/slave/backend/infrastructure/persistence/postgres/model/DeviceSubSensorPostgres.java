package pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres.model;

import javax.persistence.*;

@Entity(name = "sub_device")
public class DeviceSubSensorPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @ManyToOne
    public DeviceInformationPostgres controller;

    public Integer subDeviceRef;

    @Column(unique = true)
    public String subDeviceId;
}
