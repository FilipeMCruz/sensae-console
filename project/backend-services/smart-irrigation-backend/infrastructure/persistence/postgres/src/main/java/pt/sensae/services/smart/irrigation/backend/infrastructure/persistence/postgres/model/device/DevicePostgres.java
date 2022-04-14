package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.device;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("devices")
public class DevicePostgres {

    @Id
    public Long persistenceId;

    @Column("device_id")
    public String deviceId;

    @Column("device_type")
    public String deviceType;

    public DevicePostgres() {
        
    }

    public DevicePostgres(String deviceId, String deviceType) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
    }
}
