package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model;

import javax.persistence.*;

@Entity(name = "sub_device")
public class DeviceSubSensorPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @ManyToOne
    public DeviceRecordsPostgres controller;

    @Column(unique = true)
    public Integer subDeviceRef;

    public String subDeviceId;
}
