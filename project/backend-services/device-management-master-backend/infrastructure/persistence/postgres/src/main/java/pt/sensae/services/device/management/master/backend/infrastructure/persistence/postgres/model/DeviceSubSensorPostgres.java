package pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.model;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceSubSensorPostgres that)) return false;

        if (!Objects.equals(subDeviceRef, that.subDeviceRef)) return false;
        return Objects.equals(subDeviceId, that.subDeviceId);
    }

    @Override
    public int hashCode() {
        int result = subDeviceRef != null ? subDeviceRef.hashCode() : 0;
        result = 31 * result + (subDeviceId != null ? subDeviceId.hashCode() : 0);
        return result;
    }
}
